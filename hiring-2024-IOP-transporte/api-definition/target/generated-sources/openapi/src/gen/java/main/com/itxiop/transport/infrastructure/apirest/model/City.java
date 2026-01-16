package com.itxiop.transport.infrastructure.apirest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * City
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-16T13:10:48.635976600+01:00[Europe/Madrid]", comments = "Generator version: 7.18.0")
public class City {

  private String code;

  private String name;

  private @Nullable Double handlingCost;

  public City() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public City(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public City code(String code) {
    this.code = code;
    return this;
  }

  /**
   * City identifier (3-char unique udentifier)
   * @return code
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "code", example = "MAD", description = "City identifier (3-char unique udentifier)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public City name(String name) {
    this.name = name;
    return this;
  }

  /**
   * City full name
   * @return name
   */
  @NotNull @Size(min = 1, max = 9999) 
  @Schema(name = "name", example = "Madrid", description = "City full name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public City handlingCost(@Nullable Double handlingCost) {
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
    City city = (City) o;
    return Objects.equals(this.code, city.code) &&
        Objects.equals(this.name, city.name) &&
        Objects.equals(this.handlingCost, city.handlingCost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name, handlingCost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class City {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

