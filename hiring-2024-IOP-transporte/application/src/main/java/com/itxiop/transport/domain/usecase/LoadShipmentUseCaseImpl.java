package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LoadShipmentUseCaseImpl implements LoadShipmentUseCase {
    
    private ShipmentRepositoryPort shipmentRepositoryPort;

    @Override
    public void loadShipment(ShipmentInput shipment) {

        shipmentRepositoryPort.saveShipment(shipment);
        log.trace("Shipment loaded successfully");
    }
}
