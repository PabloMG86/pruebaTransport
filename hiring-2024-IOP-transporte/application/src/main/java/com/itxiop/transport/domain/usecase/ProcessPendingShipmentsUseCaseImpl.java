package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.route.repository.RouteRepositoryPort;
import com.itxiop.transport.domain.route.vo.RouteResult;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Find product by id use case.
 */
@Slf4j
@AllArgsConstructor
public class ProcessPendingShipmentsUseCaseImpl implements ProcessPendingShipmentsUseCase {
    
    RouteRepositoryPort routeRepositoryPort;
    
    ShipmentRepositoryPort shipmentRepositoryPort;

    @Override
    public void processPendingShipments() {

        StopWatch timeMeter = StopWatch.create();


        Map<TransportTypeEnum, List<Route>> availableRoutes = routeRepositoryPort.findAvailableRoutes(List.of(TransportTypeEnum.values()));

        shipmentRepositoryPort.findShipments().stream()
                .filter(s -> s.getStatus().equals(ShipmentStatusEnum.PENDING))
                .forEach(shipment -> {
                    
            log.info("Processing shipment: {}", shipment);
            timeMeter.start();

            // Basic Path #0 Single iteration over one concrete shipment type
            List<Route> truckRoutes = availableRoutes.get(TransportTypeEnum.TRUCK);
            log.info("Processing route: {}", TransportTypeEnum.TRUCK);

            // Use Dijkstra's algorithm to find the shortest path costs from the source city to all other cities
            RouteResult routeResult = GraphProcessor.dijkstra(truckRoutes, shipment);
            algorthimResultLogger(shipment).apply(routeResult);
            stopWatchMeter(timeMeter).accept(routeResult);
            log.info("Saving route planification for shipment: {}-{}", shipment.getOrigin().getName(), shipment.getDestination().getName());
            if(routeResult != null && routeResult.route() != null) {
                shipment.setRoutePlan(routeResult.route());
                if (routeResult.cost().compareTo(Duration.between(shipment.getDepartureDate(), shipment.getExpectedArrivalDate())) < 0) {
                    shipment.setStatus(ShipmentStatusEnum.PLANNED);
                } else {
                    shipment.setStatus(ShipmentStatusEnum.DISCARDED);
                }
            }
            else{
                shipment.setStatus(ShipmentStatusEnum.DISCARDED);
            }

            shipmentRepositoryPort.saveShipmentPlanification(shipment);
            timeMeter.stop();
            log.info("Route saved in {} ms", timeMeter.getTime());
            timeMeter.reset();
        });
    }

    private static Consumer<RouteResult> stopWatchMeter(StopWatch timeMeter) {
        return result -> {

            synchronized (timeMeter) {
                timeMeter.split();
                log.info("Route processed in {} ms", timeMeter.getSplitTime());
                timeMeter.unsplit();
            }
        };
    }

    private static Function<RouteResult, RouteResult> algorthimResultLogger(Shipment shipment) {
        return result -> {

            // Print the shortest path costs
            if (log.isTraceEnabled() && result != null) {
                log.trace("Time cost from [ {} <-> {} ] is {}", shipment.getOrigin().getName(), shipment.getDestination().getName(), result.cost());
                StringBuilder sb = new StringBuilder();
                sb.append("Path: ").append(result.route().get(0).getOrigin().getName());
                result.route().forEach(r -> sb.append(String.format(" | (%s -> [%s] -> %s)", r.getOrigin().getName(), r.getTransportType(), r.getDestination().getName())));
                log.trace(sb.toString());
            }
            return result;
        };
    }
}
