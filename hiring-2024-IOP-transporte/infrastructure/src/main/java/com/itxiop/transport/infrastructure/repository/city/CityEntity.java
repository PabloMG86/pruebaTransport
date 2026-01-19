package com.itxiop.transport.infrastructure.repository.city;


import com.itxiop.transport.infrastructure.repository.shipment.ShipmentEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "CITY", schema = "PUBLIC")
public class CityEntity {

    @Id
    @Column(name = "CITY_CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HANDLING_COST")
    private BigDecimal handlingCost;

    // Relaciones no mapeadas explícitamente en ShipmentEntity (se usan códigos)
    @OneToMany
    @JoinColumn(name = "CITY_ORIGIN_FK", insertable = false, updatable = false)
    private List<ShipmentEntity> originShipments;

    @OneToMany
    @JoinColumn(name = "CITY_DESTINATION_FK", insertable = false, updatable = false)
    private List<ShipmentEntity> destinationShipments;
}