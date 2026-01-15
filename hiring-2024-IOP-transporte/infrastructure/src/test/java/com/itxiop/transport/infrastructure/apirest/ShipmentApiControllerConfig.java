package com.itxiop.transport.infrastructure.apirest;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.usecase.ProcessPendingShipmentsUseCase;
import com.itxiop.transport.infrastructure.apirest.mapper.RestShipmentMapper;
import org.instancio.Instancio;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;


@Configuration(proxyBeanMethods = false)
public class ShipmentApiControllerConfig {

  
  /**
   * Find shipment by id use case
   */
  @Bean
  public ProcessPendingShipmentsUseCase findProductByIdUseCase() {
    
    final Shipment shipment = Instancio.create(Shipment.class);
    final ProcessPendingShipmentsUseCase processPendingShipmentsUseCase = mock(ProcessPendingShipmentsUseCase.class);
    //Mockito.when(processPendingShipmentsUseCase.findShipmentById(Mockito.any())).thenReturn(shipment);
    return processPendingShipmentsUseCase;
  }
  
  /**
   * Shipment mapper 
   */
  @Bean
  public RestShipmentMapper shipmentMapper() {
    return spy(Mappers.getMapper(RestShipmentMapper.class));
  }
}
