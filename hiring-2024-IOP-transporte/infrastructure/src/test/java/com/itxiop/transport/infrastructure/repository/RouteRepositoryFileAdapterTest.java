package com.itxiop.transport.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import com.itxiop.transport.infrastructure.repository.city.CityRepositoryFileAdapter;
import com.itxiop.transport.infrastructure.repository.route.RouteRepositoryFileAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class RouteRepositoryFileAdapterTest {

  @Test
  void testReadGraphData() throws Exception {
    
    CityRepositoryFileAdapter cityRepositoryFileAdapter = new CityRepositoryFileAdapter();
    cityRepositoryFileAdapter.afterPropertiesSet();
    RouteRepositoryFileAdapter routeRepositoryFileAdapter = new RouteRepositoryFileAdapter(cityRepositoryFileAdapter);
    ReflectionTestUtils.setField(routeRepositoryFileAdapter, "graphFolder", "graphs/sameTruckTrainLocations");
    routeRepositoryFileAdapter.afterPropertiesSet();
    Map<TransportTypeEnum, List<Route>> routeBook =
        routeRepositoryFileAdapter.findAvailableRoutes(Arrays.asList(TransportTypeEnum.values()));
    assertEquals("Madrid", routeBook.get(TransportTypeEnum.TRAIN).get(0).getOrigin().getName());
  }

}