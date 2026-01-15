package com.itxiop.transport.domain.usecase;

public interface ProcessPendingShipmentsUseCase {
  
  /**
   * Process pending to calculate the optimal route
   */
  public void processPendingShipments();

}
