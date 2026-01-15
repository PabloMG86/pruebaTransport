package com.itxiop.transport.infrastructure.repository.shipment;

import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
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

    ShipmentEntity entity = shipmentH2Repository.findById(id).orElseThrow();
    Shipment shipment = shipmentEntityMapper.toDomainEntity(entity);
     log.info("Loading shipment with id: {}", shipment.getId());

    // TODO #2: Set routes in shipment looking at routes map
    // TODO #2: Set origin and destination in shipment from cityRepository
    return shipment;
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

      // TODO #2: Set routes origin and destination
      return shipment;
    }).toList();
  }

  @Override
  public void deleteProcessedShipments() {
    log.trace("Delete all processed shipments");
    // TODO #4: Implemente deleted processed shipments
  }
}
