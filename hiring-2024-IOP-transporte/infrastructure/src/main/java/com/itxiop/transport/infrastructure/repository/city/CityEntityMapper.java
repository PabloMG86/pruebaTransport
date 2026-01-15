package com.itxiop.transport.infrastructure.repository.city;

import java.util.List;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.infrastructure.repository.shipment.ShipmentEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link ShipmentEntity}.
 */
@Mapper
public interface CityEntityMapper {

  /**
   * To domain entity.
   * @param city (infra)
   * @return  city (domain)
   */
  City toDomainEntity(CityEntity city);

  /**
   * To domain entities list.
   * @param cities (infra)
   * @return cities (domain)
   */
  List<City> toDomainEntities(List<CityEntity> cities);
  
}
