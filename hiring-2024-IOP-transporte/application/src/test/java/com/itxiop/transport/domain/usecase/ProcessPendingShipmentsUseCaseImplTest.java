package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.route.repository.RouteRepositoryPort;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessPendingShipmentsUseCaseImplTest {
    
    @Mock
    RouteRepositoryPort routeRepositoryPort;

    @Mock
    ShipmentRepositoryPort shipmentRepositoryPort;
    @Test
    void dijkstraBasicTest(){

        City cityA = City.of(UUID.randomUUID().toString(), "A" , BigDecimal.ZERO);
        City cityB = City.of(UUID.randomUUID().toString(), "B" , BigDecimal.ZERO);
        City cityC = City.of(UUID.randomUUID().toString(), "C" , BigDecimal.ZERO);
        City cityD = City.of(UUID.randomUUID().toString(), "D" , BigDecimal.ZERO);
        City cityE = City.of(UUID.randomUUID().toString(), "E" , BigDecimal.ZERO);
        
        List<Route> routes = List.of(
                Route.of(UUID.randomUUID(), cityA, cityB, TransportTypeEnum.TRUCK, Duration.of(3, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityA, cityC, TransportTypeEnum.TRUCK, Duration.of(1, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityB, cityC, TransportTypeEnum.TRUCK, Duration.of(7, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityB, cityD, TransportTypeEnum.TRUCK, Duration.of(5, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityB, cityE, TransportTypeEnum.TRUCK, Duration.of(1, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityC, cityD, TransportTypeEnum.TRUCK, Duration.of(2, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityD, cityE, TransportTypeEnum.TRUCK, Duration.of(7, ChronoUnit.HOURS))
        );

        List<Shipment> shipments = List.of(Shipment.of(UUID.randomUUID(), 
                OffsetDateTime.of(2024, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2024, 1, 1, 13, 0, 0, 0, ZoneOffset.UTC), cityA, cityE, ShipmentStatusEnum.PENDING, null));
        doReturn(Map.of(TransportTypeEnum.TRUCK, routes)).when(routeRepositoryPort).findAvailableRoutes(any());
        doReturn(shipments).when(shipmentRepositoryPort).findShipments();
        
        ProcessPendingShipmentsUseCase processPendingShipmentsUseCase = new ProcessPendingShipmentsUseCaseImpl(routeRepositoryPort, shipmentRepositoryPort);


        ArgumentCaptor<Shipment> captor = ArgumentCaptor.forClass(Shipment.class);
        processPendingShipmentsUseCase.processPendingShipments();

        verify(shipmentRepositoryPort, times(1)).saveShipmentPlanification(captor.capture());
        Shipment shipment = captor.getValue();
        assertEquals(shipment.getOrigin(), shipment.getRoutePlan().get(0).getOrigin());
        assertEquals(shipment.getDestination(), shipment.getRoutePlan().get(shipment.getRoutePlan().size() - 1).getDestination());
        Duration routeDuration = shipment.getRoutePlan().stream().map(Route::getCost).reduce(Duration::plus).get();
        assertEquals(Duration.of(4, ChronoUnit.HOURS), routeDuration);
        assertThat(routeDuration, lessThanOrEqualTo(Duration.between(shipment.getDepartureDate(), shipment.getExpectedArrivalDate())));
        shipment.getRoutePlan().forEach(System.out::println);
    }
    
    @Test
    void dijkstraComplexScenarioTest(){

        City cityA = City.of(UUID.randomUUID().toString(), "A" , BigDecimal.ZERO);
        City cityB = City.of(UUID.randomUUID().toString(), "B" , BigDecimal.ZERO);
        City cityC = City.of(UUID.randomUUID().toString(), "C" , BigDecimal.ZERO);
        City cityD = City.of(UUID.randomUUID().toString(), "D" , BigDecimal.ZERO);
        City cityE = City.of(UUID.randomUUID().toString(), "E" , BigDecimal.ZERO);
        City cityF = City.of(UUID.randomUUID().toString(), "F" , BigDecimal.ZERO);
        City cityG = City.of(UUID.randomUUID().toString(), "G" , BigDecimal.ZERO);
        City cityH = City.of(UUID.randomUUID().toString(), "H" , BigDecimal.ZERO);
        City cityI = City.of(UUID.randomUUID().toString(), "I" , BigDecimal.ZERO);

        List<Route> routes = List.of(
                Route.of(UUID.randomUUID(), cityA, cityB, TransportTypeEnum.TRUCK, Duration.of(4, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityA, cityH, TransportTypeEnum.TRUCK, Duration.of(8, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityB, cityC, TransportTypeEnum.TRUCK, Duration.of(8, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityB, cityH, TransportTypeEnum.TRUCK, Duration.of(11, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityC, cityD, TransportTypeEnum.TRUCK, Duration.of(7, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityC, cityF, TransportTypeEnum.TRUCK, Duration.of(4, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityC, cityI, TransportTypeEnum.TRUCK, Duration.of(2, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityD, cityE, TransportTypeEnum.TRUCK, Duration.of(9, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityD, cityF, TransportTypeEnum.TRUCK, Duration.of(14, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityF, cityE, TransportTypeEnum.TRUCK, Duration.of(10, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityG, cityF, TransportTypeEnum.TRUCK, Duration.of(2, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityG, cityH, TransportTypeEnum.TRUCK, Duration.of(1, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityG, cityI, TransportTypeEnum.TRUCK, Duration.of(2, ChronoUnit.HOURS)),
                Route.of(UUID.randomUUID(), cityH, cityI, TransportTypeEnum.TRUCK, Duration.of(7, ChronoUnit.HOURS))
        );

        List<Shipment> shipments = List.of(Shipment.of(UUID.randomUUID(),
                OffsetDateTime.of(2024, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2024, 1, 2, 9, 0, 0, 0, ZoneOffset.UTC), cityA, cityE, ShipmentStatusEnum.PENDING, null));
        doReturn(Map.of(TransportTypeEnum.TRUCK, routes)).when(routeRepositoryPort).findAvailableRoutes(any());
        doReturn(shipments).when(shipmentRepositoryPort).findShipments();

        ProcessPendingShipmentsUseCase processPendingShipmentsUseCase = new ProcessPendingShipmentsUseCaseImpl(routeRepositoryPort, shipmentRepositoryPort);

        ArgumentCaptor<Shipment> captor = ArgumentCaptor.forClass(Shipment.class);
        processPendingShipmentsUseCase.processPendingShipments();

        verify(shipmentRepositoryPort, times(1)).saveShipmentPlanification(captor.capture());
        Shipment shipment = captor.getValue();
        assertEquals(shipment.getOrigin(), shipment.getRoutePlan().get(0).getOrigin());
        assertEquals(shipment.getDestination(), shipment.getRoutePlan().get(shipment.getRoutePlan().size() - 1).getDestination());
        Duration routeDuration = shipment.getRoutePlan().stream().map(Route::getCost).reduce(Duration::plus).get();
        assertEquals(Duration.of(21, ChronoUnit.HOURS), routeDuration);
        assertThat(routeDuration, lessThanOrEqualTo(Duration.between(shipment.getDepartureDate(), shipment.getExpectedArrivalDate())));
        shipment.getRoutePlan().forEach(System.out::println);
    }
}