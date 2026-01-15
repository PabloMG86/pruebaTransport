package com.itxiop.transport.domain.entities;

import com.itxiop.transport.domain.vo.TransportTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Route {

    /**
     *  Route identifier
     */
    private UUID id;

    /**
     * Route origin city location
     */
    private City origin;

    /**
     * Route destination city location
     */
    private City destination;
    
    /**
     * Transport type
     */
    private TransportTypeEnum transportType;
    
    /**
     * Time-cost associated with the route. To simplify, a route has a fixed cost.
     */
    private Duration cost;
}
