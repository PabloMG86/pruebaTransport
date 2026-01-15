package com.itxiop.transport.domain.shipment.vo;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import lombok.*;

@Value(staticConstructor = "of")
@Builder
public class ShipmentInput {
  
  UUID id;
  OffsetDateTime departureDate;
  OffsetDateTime expectedArrivalDate;
  City origin;
  City destination;
  ShipmentStatusEnum status = ShipmentStatusEnum.PENDING;
}
