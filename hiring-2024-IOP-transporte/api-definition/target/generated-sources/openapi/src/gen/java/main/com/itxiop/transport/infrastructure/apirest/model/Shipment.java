package com.itxiop.transport.infrastructure.apirest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.itxiop.transport.infrastructure.apirest.model.City;
import com.itxiop.transport.infrastructure.apirest.model.Route;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Shipment aggregated information
 */

@Schema(name = "Shipment", description = "Shipment aggregated information")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-16T13:31:59.507203200+01:00[Europe/Madrid]", comments = "Generator version: 7.18.0")
public class Shipment {

  private UUID shipmentId;

  private City originCity;

  private City destinationCity;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime departureDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime expectedArrivalDate;

  /**
   * Shipment status
   */
  public enum StatusEnum {
    PENDING("PENDING"),
    
    DISCARDED("DISCARDED"),
    
    PLANNED("PLANNED");

    private final String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  @Valid
  private List<@Valid Route> routePlan = new ArrayList<>();

  public Shipment() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Shipment(UUID shipmentId, City originCity, City destinationCity, StatusEnum status) {
    this.shipmentId = shipmentId;
    this.originCity = originCity;
    this.destinationCity = destinationCity;
    this.status = status;
  }

  public Shipment shipmentId(UUID shipmentId) {
    this.shipmentId = shipmentId;
    return this;
  }

  /**
   * Shipment identifier (UUID)
   * @return shipmentId
   */
  @NotNull @Valid 
  @Schema(name = "shipmentId", description = "Shipment identifier (UUID)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shipmentId")
  public UUID getShipmentId() {
    return shipmentId;
  }

  public void setShipmentId(UUID shipmentId) {
    this.shipmentId = shipmentId;
  }

  public Shipment originCity(City originCity) {
    this.originCity = originCity;
    return this;
  }

  /**
   * Get originCity
   * @return originCity
   */
  @NotNull @Valid 
  @Schema(name = "originCity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("originCity")
  public City getOriginCity() {
    return originCity;
  }

  public void setOriginCity(City originCity) {
    this.originCity = originCity;
  }

  public Shipment destinationCity(City destinationCity) {
    this.destinationCity = destinationCity;
    return this;
  }

  /**
   * Get destinationCity
   * @return destinationCity
   */
  @NotNull @Valid 
  @Schema(name = "destinationCity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destinationCity")
  public City getDestinationCity() {
    return destinationCity;
  }

  public void setDestinationCity(City destinationCity) {
    this.destinationCity = destinationCity;
  }

  public Shipment departureDate(@Nullable OffsetDateTime departureDate) {
    this.departureDate = departureDate;
    return this;
  }

  /**
   * Departure date
   * @return departureDate
   */
  @Valid 
  @Schema(name = "departureDate", description = "Departure date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("departureDate")
  public @Nullable OffsetDateTime getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(@Nullable OffsetDateTime departureDate) {
    this.departureDate = departureDate;
  }

  public Shipment expectedArrivalDate(@Nullable OffsetDateTime expectedArrivalDate) {
    this.expectedArrivalDate = expectedArrivalDate;
    return this;
  }

  /**
   * Expected arrival date
   * @return expectedArrivalDate
   */
  @Valid 
  @Schema(name = "expectedArrivalDate", description = "Expected arrival date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expectedArrivalDate")
  public @Nullable OffsetDateTime getExpectedArrivalDate() {
    return expectedArrivalDate;
  }

  public void setExpectedArrivalDate(@Nullable OffsetDateTime expectedArrivalDate) {
    this.expectedArrivalDate = expectedArrivalDate;
  }

  public Shipment status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Shipment status
   * @return status
   */
  @NotNull 
  @Schema(name = "status", description = "Shipment status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Shipment routePlan(List<@Valid Route> routePlan) {
    this.routePlan = routePlan;
    return this;
  }

  public Shipment addRoutePlanItem(Route routePlanItem) {
    if (this.routePlan == null) {
      this.routePlan = new ArrayList<>();
    }
    this.routePlan.add(routePlanItem);
    return this;
  }

  /**
   * Planned route for the shipment
   * @return routePlan
   */
  @Valid 
  @Schema(name = "routePlan", description = "Planned route for the shipment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("routePlan")
  public List<@Valid Route> getRoutePlan() {
    return routePlan;
  }

  public void setRoutePlan(List<@Valid Route> routePlan) {
    this.routePlan = routePlan;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Shipment shipment = (Shipment) o;
    return Objects.equals(this.shipmentId, shipment.shipmentId) &&
        Objects.equals(this.originCity, shipment.originCity) &&
        Objects.equals(this.destinationCity, shipment.destinationCity) &&
        Objects.equals(this.departureDate, shipment.departureDate) &&
        Objects.equals(this.expectedArrivalDate, shipment.expectedArrivalDate) &&
        Objects.equals(this.status, shipment.status) &&
        Objects.equals(this.routePlan, shipment.routePlan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shipmentId, originCity, destinationCity, departureDate, expectedArrivalDate, status, routePlan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Shipment {\n");
    sb.append("    shipmentId: ").append(toIndentedString(shipmentId)).append("\n");
    sb.append("    originCity: ").append(toIndentedString(originCity)).append("\n");
    sb.append("    destinationCity: ").append(toIndentedString(destinationCity)).append("\n");
    sb.append("    departureDate: ").append(toIndentedString(departureDate)).append("\n");
    sb.append("    expectedArrivalDate: ").append(toIndentedString(expectedArrivalDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    routePlan: ").append(toIndentedString(routePlan)).append("\n");
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

