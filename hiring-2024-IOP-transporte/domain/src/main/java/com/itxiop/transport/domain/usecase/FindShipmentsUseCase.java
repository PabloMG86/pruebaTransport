package com.itxiop.transport.domain.usecase;

import java.util.List;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;

public interface FindShipmentsUseCase {
  
  public List<Shipment> findShipments();

}
