package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class FindShipmentsUseCaseImpl implements FindShipmentsUseCase {
    
    private ShipmentRepositoryPort shipmentRepositoryPort;
    @Override
    public List<Shipment> findShipments() {
        
        log.trace("Find current shipments");
        return shipmentRepositoryPort.findShipments();
    }
}
