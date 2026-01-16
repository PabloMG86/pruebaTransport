package com.itxiop.transport.infrastructure.repository.shipment;

import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.exceptions.CoreRuntimeException;
import com.itxiop.transport.domain.exceptions.ResourceNotFoundException;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p> Implementation of {@link ShipmentRepositoryPort} with H2 database </p>
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ShipmentH2RepositoryAdapter implements ShipmentRepositoryPort {
  
  // Memory handled routes
  private Map<UUID, List<Route>> routes = new HashMap<>();

  private final ShipmentH2Repository shipmentH2Repository;

  private final CityRepositoryPort cityRepositoryPort;

  private final ShipmentEntityMapper shipmentEntityMapper;
  
  public Shipment findShipmentById(UUID id) {
    return hydrateShipmentWithDetails(id);
  }

  @Override
  public void saveShipment(ShipmentInput shipment) {
    log.trace("Saving shipment with id: {}", shipment.getId());
    shipmentH2Repository.save(shipmentEntityMapper.fromDomainVO(shipment));
    
  }

  @Override
  public void saveShipmentPlanification(Shipment shipment) {
    log.trace("Saving shipment planification with id: {}", shipment.getId());
    shipmentH2Repository.save(shipmentEntityMapper.fromDomainEntity(shipment));
    routes.put(shipment.getId(), shipment.getRoutePlan());
  }

  @Override
  public void deleteShipment(Shipment shipment) {
    log.trace("Delete shipment with id: {}", shipment.getId());
    shipmentH2Repository.deleteById(shipment.getId());
  }

  @Override
  public List<Shipment> findShipments() {
    log.trace("Find all shipments");

    List<ShipmentEntity> entities = shipmentH2Repository.findAll();

      return entities.stream().map(entity -> {
      Shipment shipment = shipmentEntityMapper.toDomainEntity(entity);

      shipment.setRoutePlan(routes.getOrDefault(shipment.getId(), List.of()));
      try {
        shipment.setOrigin(cityRepositoryPort.findByCityCode(entity.getOriginCode()));
        shipment.setDestination(cityRepositoryPort.findByCityCode(entity.getDestinationCode()));
      } catch (ResourceNotFoundException e) {
        throw new CoreRuntimeException("City not found for shipment " + entity.getId(), e);
      }
      return shipment;
    }).toList();
  }

  @Override
  public void deleteProcessedShipments() {
    log.trace("Delete all processed shipments");
    int deleted = shipmentH2Repository.deleteProcessed(com.itxiop.transport.domain.vo.ShipmentStatusEnum.PENDING);
    routes.keySet().removeIf(id -> !shipmentH2Repository.existsById(id));
    log.info("Deleted {} processed shipments", deleted);
  }

  private Shipment hydrateShipmentWithDetails(UUID id) {
    // Consulta join para evitar N+1; si falla, degradar al método clásico
    try {
      List<Object> rows = shipmentH2Repository.findShipmentDetails(id);
      if (rows != null && !rows.isEmpty()) {
        Object first = rows.get(0);
        if (first instanceof Object[] row) {
          Shipment shipment = new Shipment();
          shipment.setId((UUID) row[0]);
          shipment.setDepartureDate((java.time.OffsetDateTime) row[1]);
          shipment.setExpectedArrivalDate((java.time.OffsetDateTime) row[2]);
          shipment.setStatus(com.itxiop.transport.domain.vo.ShipmentStatusEnum.valueOf(row[5].toString()));
          shipment.setOrigin(toCity(row[6], row[7], row[8]));
          shipment.setDestination(toCity(row[9], row[10], row[11]));
          shipment.setRoutePlan(routes.getOrDefault(shipment.getId(), List.of()));
          return shipment;
        } else if (first instanceof UUID) {
          // Algunas BD/driver devuelven la primera columna directamente cuando solo hay una fila/columna
          log.debug("Detail query returned UUID only; ignoring and using fallback for shipment {}", id);
        } else {
          log.warn("Detail query returned unexpected type {} for shipment {}", first.getClass(), id);
        }
      }
    } catch (Exception e) {
      log.warn("Fallback to standard load for shipment {} due to detail query failure: {}", id, e.getMessage());
    }

    // Fallback al mapeo original
    ShipmentEntity entity = shipmentH2Repository.findById(id).orElseThrow();
    Shipment shipment = shipmentEntityMapper.toDomainEntity(entity);
    shipment.setRoutePlan(routes.getOrDefault(shipment.getId(), List.of()));
    try {
      shipment.setOrigin(cityRepositoryPort.findByCityCode(entity.getOriginCode()));
      shipment.setDestination(cityRepositoryPort.findByCityCode(entity.getDestinationCode()));
    } catch (ResourceNotFoundException e) {
      throw new CoreRuntimeException("City not found for shipment " + entity.getId(), e);
    }
    return shipment;
  }

  private City toCity(Object code, Object name, Object handling) {
    if (code == null) {
      return null;
    }
    return City.of(
        code.toString(),
        name == null ? null : name.toString(),
        handling == null ? null : new java.math.BigDecimal(handling.toString()));
  }
}
