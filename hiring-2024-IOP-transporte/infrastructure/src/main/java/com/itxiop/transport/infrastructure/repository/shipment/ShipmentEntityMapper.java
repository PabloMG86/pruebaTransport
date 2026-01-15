package com.itxiop.transport.infrastructure.repository.shipment;

import java.util.List;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import com.itxiop.transport.infrastructure.repository.city.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link ShipmentEntity}.
 */
@Mapper
public interface ShipmentEntityMapper {

  /**
   * To domain entity.
   * @param shipment (infra)
   * @return  Shipment (domain)
   */
  @Mapping(target = "origin", expression = "java(toCity(shipment.getOrigin(), shipment.getOriginCode()))")
  @Mapping(target = "destination", expression = "java(toCity(shipment.getDestination(), shipment.getDestinationCode()))")
  Shipment toDomainEntity(ShipmentEntity shipment);

  /**
   * To domain entities list.
   * @param shipments (infra)
   * @return shipments (domain)
   */
  List<Shipment> toDomainEntities(List<ShipmentEntity> shipments);

  /**
   * To infra entity
   * 
   * @param shipmentInput (domain)
   * @return shipmentEntity (infra)
   */
  @Mapping(target = "origin", ignore = true)
  @Mapping(target = "destination", ignore = true)
  @Mapping(target = "originCode", source = "origin.code")
  @Mapping(target = "destinationCode", source = "destination.code")
  ShipmentEntity fromDomainVO(ShipmentInput shipmentInput);

  /**
   * To infra entity
   *
   * @param shipment (domain)
   * @return shipmentEntity (infra)
   */
  @Mapping(target = "origin", ignore = true)
  @Mapping(target = "destination", ignore = true)
  @Mapping(target = "originCode", source = "origin.code")
  @Mapping(target = "destinationCode", source = "destination.code")
  ShipmentEntity fromDomainEntity(Shipment shipment);

  /**
   * Creates a domain City from a stored entity or code.
   */
  default City toCity(CityEntity cityEntity, String cityCode) {
    if (cityEntity != null) {
      return City.of(cityEntity.getCode(), cityEntity.getName(), cityEntity.getHandlingCost());
    }
    return cityCode == null ? null : City.of(cityCode, null, null);
  }

}
