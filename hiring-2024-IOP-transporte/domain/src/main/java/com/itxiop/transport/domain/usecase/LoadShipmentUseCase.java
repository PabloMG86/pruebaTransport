package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.shipment.vo.ShipmentInput;

public interface LoadShipmentUseCase {

    /**
     * Loads a new shipment in the system
     * @param shipment entity
     */
    public void loadShipment(ShipmentInput shipment);
}
