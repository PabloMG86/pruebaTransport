package com.itxiop.transport.domain.usecase;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.route.vo.RouteResult;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.*;

@Slf4j
public class GraphProcessor {

    private record CityStop(City city, Duration cost, TransportTypeEnum transportTypeEnum) {}
    

    public static RouteResult dijkstra(List<Route> routes, Shipment shipment) {
        
        Set<City> visited = new HashSet<>();
        Map<City, CityStop> shortestRoutes = new HashMap<>();
        Map<City, Map<City, Route>> edges = new HashMap<>();

        log.trace("*** Evaluating dijkstra shortest path algorithm:");

        // Traverse routes to create a map used to find the route between any two connected cities.
        for (Route route : routes) {
            City origin = route.getOrigin();
            City destination = route.getDestination();

            edges.putIfAbsent(origin, new HashMap<>());
            edges.get(origin).put(destination, route);

            edges.putIfAbsent(destination, new HashMap<>());
            edges.get(destination).put(origin, route);
        }
        
        Map<City, City> previous = new HashMap<>();
        
        // Queue to keep in order the not yet visited cities with lower cost first starting with the origin city
        PriorityQueue<City> queue = new PriorityQueue<>(Comparator.comparing(city -> shortestRoutes.get(city).cost()));
        queue.add(shipment.getOrigin());
        
        // Initial distance set to zero
        shortestRoutes.put(shipment.getOrigin(), new CityStop(shipment.getOrigin(), Duration.ZERO, null));
        
        while (!queue.isEmpty()) {
            
            City current = queue.poll();
            if(visited.contains(current)){
                log.trace("Discarding node [{}] as it has already been visited", current.getName());
                continue;
            }
            log.trace("Visiting node [{}]", current.getName());
                    
            // For each neighbor of the current city
            for (Map.Entry<City, Route> edge : edges.getOrDefault(current, Collections.emptyMap()).entrySet()) {
                City neighbor = edge.getKey();
                if(!visited.contains(neighbor)) {
                    Route route = edge.getValue();
                    CityStop tentativeRoute = new CityStop(neighbor, shortestRoutes.get(current).cost().plus(route.getCost()), route.getTransportType());
                    log.trace(" >> neighbor: [{}] tentative cost: [{}] by {}", neighbor.getName(), tentativeRoute.cost(), tentativeRoute.transportTypeEnum);
                    if (!shortestRoutes.containsKey(neighbor) || shortestRoutes.get(neighbor).cost().compareTo(tentativeRoute.cost()) > 0) {
                        shortestRoutes.put(neighbor, tentativeRoute);
                        previous.put(neighbor, current);
                        queue.add(neighbor);
                        log.trace(" >> [✔] tentative neighbor with a shorter route. Adding to path");
                    }
                }
            }
            log.trace(" >> Marking node [{}] as visited", current.getName());
            visited.add(current);
        }
        
        LinkedList<Route> path = new LinkedList<>();
        City currentCity = null;
        City prevCity = shipment.getDestination();
        while(prevCity != shipment.getOrigin()){
            currentCity = prevCity;
            prevCity = previous.get(currentCity);
            Route route = edges.getOrDefault(prevCity, Collections.emptyMap()).get(currentCity);
            if(route != null){
                path.addFirst(route);
            }
            else{
                log.trace("*** Evaluation finished without a valid route");
                return null;
            }
        }
        log.trace("*** Evaluation finished with Path {}", path);
        return new RouteResult(path, shortestRoutes.get(shipment.getDestination()).cost());
    }
}
