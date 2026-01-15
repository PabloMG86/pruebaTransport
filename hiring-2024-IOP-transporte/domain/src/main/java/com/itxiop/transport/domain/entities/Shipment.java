package com.itxiop.transport.domain.entities;

import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Shipment {

  /**
   *  Shipment identifier
   */
  private UUID id;

  /**
   *  Departure date
   */
  private OffsetDateTime departureDate;
  
  /**
   *  Expected arrival date limit
   */
  private OffsetDateTime expectedArrivalDate;

  /**
   * Shipment origin city location
   */
  private City origin;

  /**
   * Shipment destination city location
   */
  private City destination;
  
  /**
   * Shipment status
   */
  private ShipmentStatusEnum status;

  /**
   * Route planned for the shipment
   */
  private List<Route> routePlan;
    
}
