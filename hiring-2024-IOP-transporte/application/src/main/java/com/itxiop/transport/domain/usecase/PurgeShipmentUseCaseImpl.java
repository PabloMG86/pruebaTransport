package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.usecase.PurgeShipmentUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PurgeShipmentUseCaseImpl implements PurgeShipmentUseCase {

    ShipmentRepositoryPort shipmentRepositoryPort;
    
    @Override
    public void purgePlannedShipments() {

        log.trace("Purging processed shipments");
        shipmentRepositoryPort.deleteProcessedShipments();
        
        
        
    }
}
