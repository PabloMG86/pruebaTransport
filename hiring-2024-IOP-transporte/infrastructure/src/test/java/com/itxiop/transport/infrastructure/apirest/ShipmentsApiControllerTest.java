package com.itxiop.transport.infrastructure.apirest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itxiop.transport.domain.usecase.FindShipmentUseCase;
import com.itxiop.transport.domain.usecase.FindShipmentsUseCase;
import com.itxiop.transport.domain.usecase.LoadShipmentUseCase;
import com.itxiop.transport.domain.usecase.ProcessPendingShipmentsUseCase;
import com.itxiop.transport.domain.usecase.PurgeShipmentUseCase;
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

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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

}
