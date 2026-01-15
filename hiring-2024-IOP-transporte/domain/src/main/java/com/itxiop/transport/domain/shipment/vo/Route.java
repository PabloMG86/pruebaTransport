package com.itxiop.transport.domain.shipment.vo;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@AllArgsConstructor
public class Route {

    UUID id;
    City origin;
    City destination;
    TransportTypeEnum transportType;
    Duration cost;
}
