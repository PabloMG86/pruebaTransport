package com.itxiop.transport.domain.shipment.repository;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;

import java.util.List;
import java.util.UUID;

public interface ShipmentRepositoryPort {
    
    /**
     * Save a shipment entity
     * 
     * @param shipment the shipment entity
     */
    void saveShipment(ShipmentInput shipment);

  /**
   * Set shipment planification
   * @param shipment entity with outdated route plan
   */
  void saveShipmentPlanification(Shipment shipment);

    /**
     * Delete a shipment entity
     * 
     * @param shipment the shipment entity
     */
    void deleteShipment(Shipment shipment);

  /**
   * Find shipment by UUID identifier
   * @param id UUID identifier
   * @return Shipment entity
   */
  Shipment findShipmentById(UUID id);

  /**
   * Find all shipments
   * @return the list of shipments
   */
  List<Shipment> findShipments();

  /**
   * Delete all processed shipments (Status != PENDING)
   */
  void deleteProcessedShipments();
}
