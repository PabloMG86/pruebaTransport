package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoadShipmentUseCaseImplTest {

  @Mock
  ShipmentRepositoryPort shipmentRepositoryPort;

  private LoadShipmentUseCaseImpl loadShipmentUseCase;

  @BeforeEach
  void setUp() {
    loadShipmentUseCase = new LoadShipmentUseCaseImpl(shipmentRepositoryPort);
  }

  /**
   * Load shipment test
   */
  @Test
  void loadShipmentDelegatesToRepository() {
    ShipmentInput shipmentInput = ShipmentInput.builder()
        .id(UUID.randomUUID())
        .origin(City.of("SVQ", null, null))
        .destination(City.of("MAD", null, null))
        .departureDate(OffsetDateTime.now())
        .expectedArrivalDate(OffsetDateTime.now().plusDays(1))
        .status(ShipmentStatusEnum.PENDING)
        .build();

    loadShipmentUseCase.loadShipment(shipmentInput);

    verify(shipmentRepositoryPort).saveShipment(shipmentInput);
  }

}