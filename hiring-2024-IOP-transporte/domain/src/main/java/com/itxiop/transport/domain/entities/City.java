package com.itxiop.transport.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class City {

    /**
     * City identifier (3-char unique udentifier)
     */
    private String code;

    /**
     *  City name
     */
    private String name;

    /**
     *  Handling operation costs associated to move shipments from one transport type to another.
     *  To simplify, any kind of movement have between one transport type to another has the same cost.
     */
    private BigDecimal handlingCost;
}
