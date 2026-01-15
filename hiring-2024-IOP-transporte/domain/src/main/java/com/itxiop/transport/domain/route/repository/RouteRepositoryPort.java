package com.itxiop.transport.domain.route.repository;


import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.vo.TransportTypeEnum;

import java.util.List;
import java.util.Map;

public interface RouteRepositoryPort {
    
    /**
     * Find all available routes
     * @param transportTypeFilter list of transport types to filter
     * @return list of routes grouped by transport type
     */
    Map<TransportTypeEnum, List<Route> > findAvailableRoutes(List<TransportTypeEnum> transportTypeFilter);
}
