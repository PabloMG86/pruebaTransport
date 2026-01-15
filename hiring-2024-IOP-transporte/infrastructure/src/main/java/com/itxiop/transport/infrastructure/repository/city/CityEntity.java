package com.itxiop.transport.infrastructure.repository.city;


import com.itxiop.transport.infrastructure.repository.shipment.ShipmentEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CityEntity {

    /**
     *  Truck identifier
     */

    private String code;

    private String name;

    private BigDecimal handlingCost;

    private List<ShipmentEntity> originShipments;

    private List<ShipmentEntity> destinationShipments;
}