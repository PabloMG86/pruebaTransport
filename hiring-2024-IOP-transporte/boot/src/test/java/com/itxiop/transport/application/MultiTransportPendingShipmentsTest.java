package com.itxiop.transport.application;

import com.itxiop.transport.ApplicationContextTest;
import com.itxiop.transport.config.UseCaseProviderFactory;
import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.usecase.ProcessPendingShipmentsUseCase;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import com.itxiop.transport.infrastructure.repository.city.CityRepositoryFileAdapter;
import com.itxiop.transport.infrastructure.repository.route.RouteRepositoryFileAdapter;
import org.instancio.Assign;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UseCaseProviderFactory.class, RouteRepositoryFileAdapter.class, CityRepositoryFileAdapter.class}, initializers = ConfigDataApplicationContextInitializer.class)
public class MultiTransportPendingShipmentsTest {

    @MockBean
    ShipmentRepositoryPort shipmentRepositoryPort;

    @Autowired
    ProcessPendingShipmentsUseCase processPendingShipmentsUseCase;

    @Autowired
    CityRepositoryFileAdapter cityRepositoryFileAdapter;

    @Value("${graph.folder}")
    private String testGraphFolder;

    @ParameterizedTest(name = "Multi transport pending shipments test")
    @MethodSource("provideCityCombinations")
    void multiTransportPendingShipmentsTest(String originCityCode, String destinationCityCode, Long expectedCost){

        // Change the graph folder to the one with multiple transport types to execute these tests.
        Assumptions.assumeTrue(testGraphFolder.equals("multipleTransportTypes"), "Test configured only 4 multiple transport types");
        Shipment shipment = Instancio.of(Shipment.class)
                .set(field(Shipment::getStatus), ShipmentStatusEnum.PENDING)
                .set(field(Shipment::getOrigin), cityRepositoryFileAdapter.findByCityCode(originCityCode))
                .set(field(Shipment::getDestination), cityRepositoryFileAdapter.findByCityCode(destinationCityCode))
                .assign(Assign.valueOf(Shipment::getDepartureDate).to(Shipment::getExpectedArrivalDate).as((OffsetDateTime date) -> date.plusDays(2)))
                .create();
        List<Shipment> shipments = List.of(shipment);
        doReturn(shipments).when(shipmentRepositoryPort).findShipments();
        processPendingShipmentsUseCase.processPendingShipments();

        // Check shipment was correctly planned

        ArgumentCaptor<Shipment> shipmentCaptor = ArgumentCaptor.forClass(Shipment.class);
        verify(shipmentRepositoryPort).saveShipmentPlanification(shipmentCaptor.capture());
        Shipment savedShipment = shipmentCaptor.getValue();
        assertNotNull(savedShipment);
        assertEquals(ShipmentStatusEnum.PLANNED, savedShipment.getStatus());
        assertEquals(Duration.of(expectedCost, ChronoUnit.HOURS), savedShipment.getRoutePlan().stream().reduce(Duration.ZERO, (acc, route) -> acc.plus(route.getCost()), Duration::plus));
    }

    static Stream<Arguments> provideCityCombinations() {
        return Stream.of(
                Arguments.of("MAD", "VIE", 11L, true),
                Arguments.of("MAD", "REY", 42L, true),
                Arguments.of("LON", "WAR", 8L, true)
        );
    }


}
