package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class FindShipmentUseCaseImpl implements FindShipmentUseCase {

    private final ShipmentRepositoryPort shipmentRepositoryPort;

    @Override
    public Shipment findShipmentById(UUID id) {
        
        log.trace("Find shipment by id {}", id);
        return this.shipmentRepositoryPort.findShipmentById(id);
    }
}
