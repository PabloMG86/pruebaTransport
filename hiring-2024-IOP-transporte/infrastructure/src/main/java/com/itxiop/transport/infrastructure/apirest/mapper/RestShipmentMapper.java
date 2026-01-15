package com.itxiop.transport.infrastructure.apirest.mapper;


import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
   * Builds a City domain object using only its code.
   */
  default City toCity(String cityCode) {
    return cityCode == null ? null : City.of(cityCode, null, null);
  }
}
