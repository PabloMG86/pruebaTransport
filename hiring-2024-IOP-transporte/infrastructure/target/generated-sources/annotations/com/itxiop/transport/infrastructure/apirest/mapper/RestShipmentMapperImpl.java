package com.itxiop.transport.infrastructure.apirest.mapper;

import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-15T20:59:41+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class RestShipmentMapperImpl implements RestShipmentMapper {

    @Override
    public ShipmentInput toDomainShipmentInput(com.itxiop.transport.infrastructure.apirest.model.ShipmentInput shipmentInput) {
        if ( shipmentInput == null ) {
            return null;
        }

        ShipmentInput.ShipmentInputBuilder shipmentInput1 = ShipmentInput.builder();

        shipmentInput1.id( shipmentInput.getShipmentId() );
        shipmentInput1.departureDate( shipmentInput.getDepartureDate() );
        shipmentInput1.expectedArrivalDate( shipmentInput.getExpectedArrivalDate() );

        shipmentInput1.origin( toCity(shipmentInput.getOriginCityCode()) );
        shipmentInput1.destination( toCity(shipmentInput.getDestinationCityCode()) );

        return shipmentInput1.build();
    }
}
