package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Find shipment by id use case test.
 */
@ExtendWith(MockitoExtension.class)
class FindShipmentByIdUseCaseTest {

  @Mock
  private ShipmentRepositoryPort shipmentRepositoryPort;

  private FindShipmentUseCaseImpl useCase;

  @BeforeEach
  void setUp() {
    useCase = new FindShipmentUseCaseImpl(shipmentRepositoryPort);
  }

  /**
   * Find shipment by id test.
   */
  @Test
  void findShipmentByIdTest() {
    UUID id = UUID.randomUUID();
    Shipment expected = Shipment.of(id, OffsetDateTime.now(), OffsetDateTime.now().plusHours(4),
        null, null, ShipmentStatusEnum.PENDING, null);

    when(shipmentRepositoryPort.findShipmentById(id)).thenReturn(expected);

    Shipment result = useCase.findShipmentById(id);

    assertThat(result, sameInstance(expected));
    verify(shipmentRepositoryPort).findShipmentById(id);
  }


}
