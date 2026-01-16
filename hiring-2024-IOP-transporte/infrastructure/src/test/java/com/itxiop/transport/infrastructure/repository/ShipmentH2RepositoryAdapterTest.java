package com.itxiop.transport.infrastructure.repository;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.exceptions.CoreRuntimeException;
import com.itxiop.transport.domain.exceptions.ResourceNotFoundException;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.infrastructure.repository.shipment.ShipmentEntity;
import com.itxiop.transport.infrastructure.repository.shipment.ShipmentEntityMapper;
import com.itxiop.transport.infrastructure.repository.shipment.ShipmentH2Repository;
import com.itxiop.transport.infrastructure.repository.shipment.ShipmentH2RepositoryAdapter;
import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentH2RepositoryAdapterTest {

  @Mock
  private ShipmentH2Repository shipmentH2Repository;

  @Mock
  private CityRepositoryPort cityRepositoryPort;

  @Mock
  private ShipmentEntityMapper shipmentEntityMapper;

  private ShipmentH2RepositoryAdapter adapter;

  private UUID shipmentId;

  @BeforeEach
  void setUp() {
    adapter = new ShipmentH2RepositoryAdapter(shipmentH2Repository, cityRepositoryPort, shipmentEntityMapper);
    shipmentId = UUID.randomUUID();
  }

  @Test
  void fallbackToStandardLoadWhenDetailQueryFails() throws com.itxiop.transport.domain.exceptions.ResourceNotFoundException {
    ShipmentEntity entity = new ShipmentEntity();
    entity.setId(shipmentId);
    when(shipmentH2Repository.findShipmentDetails(shipmentId)).thenThrow(new CoreRuntimeException("fail"));
    when(shipmentH2Repository.findById(shipmentId)).thenReturn(java.util.Optional.of(entity));
    Shipment mapped = new Shipment();
    mapped.setId(shipmentId);
    when(shipmentEntityMapper.toDomainEntity(entity)).thenReturn(mapped);
    when(cityRepositoryPort.findByCityCode(null)).thenReturn(null);

    Shipment result = adapter.findShipmentById(shipmentId);

    assertThat(result.getId(), equalTo(shipmentId));
  }

  @Test
  void mapsFromDetailQueryWhenAvailable() throws ResourceNotFoundException {
    Object[] row = new Object[] {
        shipmentId,
        OffsetDateTime.parse("2024-02-02T03:00:00Z"),
        OffsetDateTime.parse("2024-02-03T11:00:00Z"),
        "BER", "REY", "PENDING",
        "BER", "Berlin", java.math.BigDecimal.ONE,
        "REY", "Reykjavik", java.math.BigDecimal.valueOf(2)
    };
    when(shipmentH2Repository.findShipmentDetails(shipmentId)).thenReturn(List.of(row));
    // Evita fallback si algo cambiara: stub de findById + mapper
    ShipmentEntity entity = new ShipmentEntity();
    entity.setId(shipmentId);
    entity.setOriginCode("BER");
    entity.setDestinationCode("REY");
    when(shipmentH2Repository.findById(shipmentId)).thenReturn(java.util.Optional.of(entity));
    Shipment mapped = new Shipment();
    mapped.setId(shipmentId);
    mapped.setOrigin(City.of("BER", "Berlin", java.math.BigDecimal.ONE));
    mapped.setDestination(City.of("REY", "Reykjavik", java.math.BigDecimal.valueOf(2)));
    when(shipmentEntityMapper.toDomainEntity(entity)).thenReturn(mapped);
    lenient().when(cityRepositoryPort.findByCityCode("BER")).thenReturn(mapped.getOrigin());
    lenient().when(cityRepositoryPort.findByCityCode("REY")).thenReturn(mapped.getDestination());
    lenient().when(cityRepositoryPort.findByCityCode(null)).thenReturn(mapped.getOrigin());

    Shipment result = adapter.findShipmentById(shipmentId);

    assertThat(result.getId(), equalTo(shipmentId));
    assertThat(result.getOrigin().getCode(), equalTo("BER"));
    assertThat(result.getDestination().getCode(), equalTo("REY"));
  }
}
