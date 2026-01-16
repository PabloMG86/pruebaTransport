package com.itxiop.transport.infrastructure.apirest.mapper;


import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * The interface Rest shipment mapper.
 */
@Mapper
public interface RestShipmentMapper {

  /**
   * Maps the REST shipment input to the domain VO.
   * Explicitly builds City instances using the provided city codes.
   *
   * @param shipmentInput REST payload
   * @return domain shipment input
   */
  @Mapping(target = "id", source = "shipmentId")
  @Mapping(target = "origin", expression = "java(toCity(shipmentInput.getOriginCityCode()))")
  @Mapping(target = "destination", expression = "java(toCity(shipmentInput.getDestinationCityCode()))")
  ShipmentInput toDomainShipmentInput(
      com.itxiop.transport.infrastructure.apirest.model.ShipmentInput shipmentInput);

  /**
   * Maps a domain Shipment to the REST model.
   */
  @Mapping(target = "shipmentId", source = "id")
  @Mapping(target = "originCity", source = "origin")
  @Mapping(target = "destinationCity", source = "destination")
  @Mapping(target = "routePlan", qualifiedByName = "mapRoutePlan")
  @Mapping(target = "status", expression = "java(mapStatus(shipment.getStatus()))")
  com.itxiop.transport.infrastructure.apirest.model.Shipment toRestShipment(Shipment shipment);

  /**
   * Maps a list of shipments to REST.
   */
  java.util.List<com.itxiop.transport.infrastructure.apirest.model.Shipment> toRestShipments(
      java.util.List<Shipment> shipments);

  /**
   * Builds a City domain object using only its code.
   */
  default City toCity(String cityCode) {
    return cityCode == null ? null : City.of(cityCode, null, null);
  }

  /**
   * Maps domain status to REST enum.
   */
  default com.itxiop.transport.infrastructure.apirest.model.Shipment.StatusEnum mapStatus(
      ShipmentStatusEnum status) {
    return status == null ? null
        : com.itxiop.transport.infrastructure.apirest.model.Shipment.StatusEnum.valueOf(status.name());
  }

  /**
   * Maps route plan, converting duration to ISO string.
   */
  @Named("mapRoutePlan")
  default java.util.List<com.itxiop.transport.infrastructure.apirest.model.Route> mapRoutePlan(
      java.util.List<Route> routes) {
    if (routes == null) {
      return null;
    }
    return routes.stream().map(route -> {
      var restRoute = new com.itxiop.transport.infrastructure.apirest.model.Route();
      restRoute.setId(route.getId());
      restRoute.setOrigin(toRestCity(route.getOrigin()));
      restRoute.setDestination(toRestCity(route.getDestination()));
      restRoute.setTransportType(
          route.getTransportType() == null ? null
              : com.itxiop.transport.infrastructure.apirest.model.Route.TransportTypeEnum.valueOf(
                  route.getTransportType().name()));
      restRoute.setCost(route.getCost() == null ? null : route.getCost().toString());
      restRoute.setHandlingCost(null);
      return restRoute;
    }).toList();
  }

  /**
   * Maps a domain city to REST city.
   */
  default com.itxiop.transport.infrastructure.apirest.model.City toRestCity(City city) {
    if (city == null) {
      return null;
    }
    com.itxiop.transport.infrastructure.apirest.model.City restCity =
        new com.itxiop.transport.infrastructure.apirest.model.City();
    restCity.setCode(city.getCode());
    restCity.setName(city.getName());
    restCity.setHandlingCost(city.getHandlingCost() == null ? null : city.getHandlingCost().doubleValue());
    return restCity;
  }
}
