package com.itxiop.transport.infrastructure.repository.shipment;

import java.util.List;

import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import org.mapstruct.Mapper;

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
  ShipmentEntity fromDomainVO(ShipmentInput shipmentInput);

  /**
   * To infra entity
   *
   * @param shipment (domain)
   * @return shipmentEntity (infra)
   */
  ShipmentEntity fromDomainEntity(Shipment shipment);

}
