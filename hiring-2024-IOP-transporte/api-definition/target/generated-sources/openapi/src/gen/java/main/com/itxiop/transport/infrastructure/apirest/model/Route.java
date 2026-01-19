package com.itxiop.transport.infrastructure.apirest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.itxiop.transport.infrastructure.apirest.model.City;
import java.util.UUID;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Route
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-19T07:24:44.251291200+01:00[Europe/Madrid]", comments = "Generator version: 7.18.0")
public class Route {

  private @Nullable UUID id;

  private @Nullable City origin;

  private @Nullable City destination;

  /**
   * Transport type
   */
  public enum TransportTypeEnum {
    TRUCK("TRUCK"),
    
    TRAIN("TRAIN"),
    
    SEA("SEA"),
    
    AIR("AIR");

    private final String value;

    TransportTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TransportTypeEnum fromValue(String value) {
      for (TransportTypeEnum b : TransportTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable TransportTypeEnum transportType;

  private @Nullable String cost;

  private @Nullable Double handlingCost;

  public Route id(@Nullable UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Shipment identifier (UUID)
   * @return id
   */
  @Valid 
  @Schema(name = "id", example = "123e4567-e89b-12d3-a456-426614174000", description = "Shipment identifier (UUID)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable UUID getId() {
    return id;
  }

  public void setId(@Nullable UUID id) {
    this.id = id;
  }

  public Route origin(@Nullable City origin) {
    this.origin = origin;
    return this;
  }

  /**
   * Get origin
   * @return origin
   */
  @Valid 
  @Schema(name = "origin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("origin")
  public @Nullable City getOrigin() {
    return origin;
  }

  public void setOrigin(@Nullable City origin) {
    this.origin = origin;
  }

  public Route destination(@Nullable City destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Get destination
   * @return destination
   */
  @Valid 
  @Schema(name = "destination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destination")
  public @Nullable City getDestination() {
    return destination;
  }

  public void setDestination(@Nullable City destination) {
    this.destination = destination;
  }

  public Route transportType(@Nullable TransportTypeEnum transportType) {
    this.transportType = transportType;
    return this;
  }

  /**
   * Transport type
   * @return transportType
   */
  
  @Schema(name = "transportType", example = "TRUCK", description = "Transport type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("transportType")
  public @Nullable TransportTypeEnum getTransportType() {
    return transportType;
  }

  public void setTransportType(@Nullable TransportTypeEnum transportType) {
    this.transportType = transportType;
  }

  public Route cost(@Nullable String cost) {
    this.cost = cost;
    return this;
  }

  /**
   * Route time Cost (duration in hours)
   * @return cost
   */
  
  @Schema(name = "cost", example = "T12H", description = "Route time Cost (duration in hours)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cost")
  public @Nullable String getCost() {
    return cost;
  }

  public void setCost(@Nullable String cost) {
    this.cost = cost;
  }

  public Route handlingCost(@Nullable Double handlingCost) {
    this.handlingCost = handlingCost;
    return this;
  }

  /**
   * Handling cost
   * @return handlingCost
   */
  
  @Schema(name = "handlingCost", example = "10.0", description = "Handling cost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("handlingCost")
  public @Nullable Double getHandlingCost() {
    return handlingCost;
  }

  public void setHandlingCost(@Nullable Double handlingCost) {
    this.handlingCost = handlingCost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Route route = (Route) o;
    return Objects.equals(this.id, route.id) &&
        Objects.equals(this.origin, route.origin) &&
        Objects.equals(this.destination, route.destination) &&
        Objects.equals(this.transportType, route.transportType) &&
        Objects.equals(this.cost, route.cost) &&
        Objects.equals(this.handlingCost, route.handlingCost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, origin, destination, transportType, cost, handlingCost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Route {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
    sb.append("    transportType: ").append(toIndentedString(transportType)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    handlingCost: ").append(toIndentedString(handlingCost)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

