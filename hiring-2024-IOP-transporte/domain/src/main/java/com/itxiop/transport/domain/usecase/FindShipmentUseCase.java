package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.Shipment;

import java.util.UUID;

public interface FindShipmentUseCase {

    /**
     * Find shipments by identifier
     *
     * @param id the id
     * @return the shiptment
     */
    Shipment findShipmentById(UUID id);
}
