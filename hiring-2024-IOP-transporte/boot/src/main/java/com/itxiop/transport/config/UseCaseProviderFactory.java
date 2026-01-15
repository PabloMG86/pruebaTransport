package com.itxiop.transport.config;

import com.itxiop.transport.domain.route.repository.RouteRepositoryPort;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseProviderFactory {
    
    @Bean
    public FindShipmentUseCase findShipmentUseCase(ShipmentRepositoryPort shipmentRepositoryPort) {
        return new FindShipmentUseCaseImpl(shipmentRepositoryPort);
    }

    @Bean
    public FindShipmentsUseCase findShipmentsUseCase(ShipmentRepositoryPort shipmentRepositoryPort) {
        return new FindShipmentsUseCaseImpl(shipmentRepositoryPort);
    }

    @Bean
    public ProcessPendingShipmentsUseCase processPendingShipmentsUseCase(RouteRepositoryPort routeRepositoryPort, ShipmentRepositoryPort shipmentRepositoryPort) {
        return new ProcessPendingShipmentsUseCaseImpl(routeRepositoryPort, shipmentRepositoryPort);
    }

    @Bean
    public LoadShipmentUseCase loadShipmentUseCase(ShipmentRepositoryPort shipmentRepositoryPort) {
        return new LoadShipmentUseCaseImpl(shipmentRepositoryPort);
    }

    @Bean
    public PurgeShipmentUseCase purgeShipmentUseCase(ShipmentRepositoryPort shipmentRepositoryPort) {
        return new PurgeShipmentUseCaseImpl(shipmentRepositoryPort);
    }
}
