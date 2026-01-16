package com.itxiop.transport.infrastructure.apirest.mapper;

import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import com.itxiop.transport.infrastructure.apirest.model.Shipment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T12:00:10+0100",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
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

    @Override
    public Shipment toRestShipment(com.itxiop.transport.domain.entities.Shipment shipment) {
        if ( shipment == null ) {
            return null;
        }

        Shipment shipment1 = new Shipment();

        shipment1.setShipmentId( shipment.getId() );
        shipment1.setOriginCity( toRestCity( shipment.getOrigin() ) );
        shipment1.setDestinationCity( toRestCity( shipment.getDestination() ) );
        shipment1.setRoutePlan( mapRoutePlan( shipment.getRoutePlan() ) );
        shipment1.setDepartureDate( shipment.getDepartureDate() );
        shipment1.setExpectedArrivalDate( shipment.getExpectedArrivalDate() );

        shipment1.setStatus( mapStatus(shipment.getStatus()) );

        return shipment1;
    }

    @Override
    public List<Shipment> toRestShipments(List<com.itxiop.transport.domain.entities.Shipment> shipments) {
        if ( shipments == null ) {
            return null;
        }

        List<Shipment> list = new ArrayList<Shipment>( shipments.size() );
        for ( com.itxiop.transport.domain.entities.Shipment shipment : shipments ) {
            list.add( toRestShipment( shipment ) );
        }

        return list;
    }
}
