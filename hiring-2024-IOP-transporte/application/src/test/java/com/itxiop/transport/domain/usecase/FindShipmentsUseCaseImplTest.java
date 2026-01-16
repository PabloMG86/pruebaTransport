package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindShipmentsUseCaseImplTest {

    @Mock
    ShipmentRepositoryPort shipmentRepositoryPort;

    FindShipmentsUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindShipmentsUseCaseImpl(shipmentRepositoryPort);
    }

    @Test
    void findShipments() {
        List<Shipment> expected = List.of(new Shipment());
        when(shipmentRepositoryPort.findShipments()).thenReturn(expected);

        List<Shipment> result = useCase.findShipments();

        assertThat(result).isEqualTo(expected);
        verify(shipmentRepositoryPort).findShipments();
    }
}