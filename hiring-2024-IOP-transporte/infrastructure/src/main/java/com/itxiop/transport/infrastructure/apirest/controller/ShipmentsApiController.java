package com.itxiop.transport.infrastructure.apirest.controller;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import com.itxiop.transport.domain.usecase.*;
import com.itxiop.transport.infrastructure.apirest.ShipmentsApi;
import com.itxiop.transport.infrastructure.apirest.mapper.RestShipmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * The type Product api controller.
 */
@RestController
@AllArgsConstructor
public class ShipmentsApiController implements ShipmentsApi {
  

  private ProcessPendingShipmentsUseCase processPendingShipmentsUseCase;
  
  private LoadShipmentUseCase loadShipmentUseCase;
  

  private FindShipmentsUseCase findShipmentsUseCase;


  private FindShipmentUseCase findShipmentUseCase;

  private PurgeShipmentUseCase purgeShipmentUseCase;

  private RestShipmentMapper shipmentMapper;

  @Override
  public ResponseEntity<com.itxiop.transport.infrastructure.apirest.model.Shipment> getShipment(UUID shipmentId) {
    Shipment domainShipment = findShipmentUseCase.findShipmentById(shipmentId);
    com.itxiop.transport.infrastructure.apirest.model.Shipment shipment = shipmentMapper.toRestShipment(domainShipment);
    return ResponseEntity.ok(shipment);
  }

  @Override
  public ResponseEntity<List<com.itxiop.transport.infrastructure.apirest.model.Shipment>> getShipments() {
    List<Shipment> domainShipments = findShipmentsUseCase.findShipments();
    List<com.itxiop.transport.infrastructure.apirest.model.Shipment> shipments =
        shipmentMapper.toRestShipments(domainShipments);
    return ResponseEntity.ok(shipments);
  }

  @Override
  public ResponseEntity<Void> loadShipment(
      com.itxiop.transport.infrastructure.apirest.model.ShipmentInput shipmentInput) {
    ShipmentInput domainVO = shipmentMapper.toDomainShipmentInput(shipmentInput);
    loadShipmentUseCase.loadShipment(domainVO);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> purgeShipments(){

    purgeShipmentUseCase.purgePlannedShipments();
    return ResponseEntity.ok().build();
  }



  @Override
  public ResponseEntity<Void> processPendingShipments(){

    processPendingShipmentsUseCase.processPendingShipments();
    return ResponseEntity.ok().build();
  }
}
