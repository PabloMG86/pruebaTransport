package com.itxiop.transport.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * REST Integration Tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
class ShipmentServerRestTestIT {

  private static final String MODIFIED = "MODIFIED";

  private static final String BASE_PATH = "/v1/shipment";

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Value("${test.configuration.jwt-token}")
  private String jwtToken;

  @LocalServerPort
  private int port;

  /**
   * Gets the product.
   */
  @Test
  void getProductTest() {

    /*final var requestHeaders = this.getHttpHeaders();
    final var requestEntity = new HttpEntity<>(requestHeaders);
    final var parameters = this.generateIdParam("1");

    final var response = this.testRestTemplate
        .exchange(BASE_PATH.concat("/{id}"), HttpMethod.GET, requestEntity, com.itxiop.transport.infrastructure.apirest.model.Shipment.class, parameters);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    final var shipment = response.getBody();

    assertNotNull(shipment);
    assertNotNull(shipment.getShipmentId());
    assertEquals("PRODUCT 1", shipment.getTransportId());*/
  }

  


}
