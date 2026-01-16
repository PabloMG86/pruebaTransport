package com.itxiop.transport.infrastructure.apirest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.usecase.FindShipmentUseCase;
import com.itxiop.transport.domain.usecase.FindShipmentsUseCase;
import com.itxiop.transport.domain.usecase.LoadShipmentUseCase;
import com.itxiop.transport.domain.usecase.ProcessPendingShipmentsUseCase;
import com.itxiop.transport.domain.usecase.PurgeShipmentUseCase;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import com.itxiop.transport.infrastructure.apirest.controller.ShipmentsApiController;
import com.itxiop.transport.infrastructure.apirest.mapper.RestShipmentMapperImpl;
import com.itxiop.transport.infrastructure.apirest.model.ShipmentInput;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShipmentsApiController.class)
@Import(RestShipmentMapperImpl.class)
class ShipmentsApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProcessPendingShipmentsUseCase processPendingShipmentsUseCase;

  @MockBean
  private LoadShipmentUseCase loadShipmentUseCase;

  @MockBean
  private FindShipmentsUseCase findShipmentsUseCase;

  @MockBean
  private FindShipmentUseCase findShipmentUseCase;

  @MockBean
  private PurgeShipmentUseCase purgeShipmentUseCase;

  @Test
  void loadShipmentShouldMapPayloadAndCallUseCase() throws Exception {
    ShipmentInput request = new ShipmentInput();
    UUID shipmentId = UUID.randomUUID();
    request.setShipmentId(shipmentId);
    request.setOriginCityCode("SVQ");
    request.setDestinationCityCode("MAD");
    request.setDepartureDate(OffsetDateTime.parse("2026-01-15T10:00:00Z"));
    request.setExpectedArrivalDate(OffsetDateTime.parse("2026-01-16T10:00:00Z"));

    mockMvc.perform(
            post("/shipment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    ArgumentCaptor<com.itxiop.transport.domain.shipment.vo.ShipmentInput> shipmentCaptor =
        ArgumentCaptor.forClass(com.itxiop.transport.domain.shipment.vo.ShipmentInput.class);

    verify(loadShipmentUseCase).loadShipment(shipmentCaptor.capture());

    com.itxiop.transport.domain.shipment.vo.ShipmentInput domainShipment = shipmentCaptor.getValue();
    assertThat(domainShipment.getId()).isEqualTo(shipmentId);
    assertThat(domainShipment.getOrigin().getCode()).isEqualTo("SVQ");
    assertThat(domainShipment.getDestination().getCode()).isEqualTo("MAD");
    assertThat(domainShipment.getDepartureDate()).isEqualTo(request.getDepartureDate());
    assertThat(domainShipment.getExpectedArrivalDate()).isEqualTo(request.getExpectedArrivalDate());
  }

  @Test
  void getShipmentShouldReturnMappedResponse() throws Exception {
    UUID shipmentId = UUID.randomUUID();
    Shipment shipment = Shipment.of(
        shipmentId,
        OffsetDateTime.parse("2024-02-02T03:00:00Z"),
        OffsetDateTime.parse("2024-02-03T11:00:00Z"),
        City.of("BER", "Berlin (JSON)", java.math.BigDecimal.valueOf(5.0)),
        City.of("REY", "Reykjavik (JSON)", java.math.BigDecimal.valueOf(2.0)),
        ShipmentStatusEnum.PENDING,
        null);
    when(findShipmentUseCase.findShipmentById(shipmentId)).thenReturn(shipment);

    mockMvc.perform(get("/shipment/{shipmentId}", shipmentId))
        .andExpect(status().isOk())
        .andExpect(result -> {
          String json = result.getResponse().getContentAsString();
          assertThat(json).contains("\"shipmentId\"");
          assertThat(json).contains("BER");
          assertThat(json).contains("REY");
          assertThat(json).contains("PENDING");
        });
  }

  @Test
  void getShipmentsShouldReturnListMapped() throws Exception {
    Shipment shipment1 = Shipment.of(
        UUID.fromString("13b96d13-40d9-4ef9-9fe1-1b92a309d92f"),
        OffsetDateTime.parse("2024-02-02T03:00:00Z"),
        OffsetDateTime.parse("2024-02-03T11:00:00Z"),
        City.of("BER", "Berlin (JSON)", java.math.BigDecimal.valueOf(5.0)),
        City.of("REY", "Reykjavik (JSON)", java.math.BigDecimal.valueOf(2.0)),
        ShipmentStatusEnum.DISCARDED,
        List.of(
            Route.of(UUID.randomUUID(),
                City.of("PAR", "París (JSON)", java.math.BigDecimal.valueOf(5.0)),
                City.of("BER", "Berlin (JSON)", java.math.BigDecimal.valueOf(5.0)),
                TransportTypeEnum.AIR,
                Duration.ofHours(1)),
            Route.of(UUID.randomUUID(),
                City.of("LON", "Londres (JSON)", java.math.BigDecimal.valueOf(6.0)),
                City.of("REY", "Reykjavik (JSON)", java.math.BigDecimal.valueOf(2.0)),
                TransportTypeEnum.SEA,
                Duration.ofHours(40))
        ));

    when(findShipmentsUseCase.findShipments()).thenReturn(List.of(shipment1));

    mockMvc.perform(get("/shipments"))
        .andExpect(status().isOk())
        .andExpect(result -> {
          String json = result.getResponse().getContentAsString();
          assertThat(json).contains("13b96d13-40d9-4ef9-9fe1-1b92a309d92f");
          assertThat(json).contains("routePlan");
          assertThat(json).contains("PT40H");
          assertThat(json).contains("DISCARDED");
        });
  }
}
