package com.itxiop.transport.infrastructure.repository.shipment;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.entities.Shipment;
import com.itxiop.transport.domain.shipment.vo.ShipmentInput;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T08:51:28+0100",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ShipmentEntityMapperImpl implements ShipmentEntityMapper {

    @Override
    public Shipment toDomainEntity(ShipmentEntity shipment) {
        if ( shipment == null ) {
            return null;
        }

        Shipment shipment1 = new Shipment();

        shipment1.setId( shipment.getId() );
        shipment1.setDepartureDate( shipment.getDepartureDate() );
        shipment1.setExpectedArrivalDate( shipment.getExpectedArrivalDate() );
        shipment1.setStatus( shipment.getStatus() );

        shipment1.setOrigin( toCity(shipment.getOrigin(), shipment.getOriginCode()) );
        shipment1.setDestination( toCity(shipment.getDestination(), shipment.getDestinationCode()) );

        return shipment1;
    }

    @Override
    public List<Shipment> toDomainEntities(List<ShipmentEntity> shipments) {
        if ( shipments == null ) {
            return null;
        }

        List<Shipment> list = new ArrayList<Shipment>( shipments.size() );
        for ( ShipmentEntity shipmentEntity : shipments ) {
            list.add( toDomainEntity( shipmentEntity ) );
        }

        return list;
    }

    @Override
    public ShipmentEntity fromDomainVO(ShipmentInput shipmentInput) {
        if ( shipmentInput == null ) {
            return null;
        }

        ShipmentEntity shipmentEntity = new ShipmentEntity();

        shipmentEntity.setOriginCode( shipmentInputOriginCode( shipmentInput ) );
        shipmentEntity.setDestinationCode( shipmentInputDestinationCode( shipmentInput ) );
        shipmentEntity.setDepartureDate( shipmentInput.getDepartureDate() );
        shipmentEntity.setExpectedArrivalDate( shipmentInput.getExpectedArrivalDate() );
        shipmentEntity.setId( shipmentInput.getId() );
        shipmentEntity.setStatus( shipmentInput.getStatus() );

        return shipmentEntity;
    }

    @Override
    public ShipmentEntity fromDomainEntity(Shipment shipment) {
        if ( shipment == null ) {
            return null;
        }

        ShipmentEntity shipmentEntity = new ShipmentEntity();

        shipmentEntity.setOriginCode( shipmentOriginCode( shipment ) );
        shipmentEntity.setDestinationCode( shipmentDestinationCode( shipment ) );
        shipmentEntity.setDepartureDate( shipment.getDepartureDate() );
        shipmentEntity.setExpectedArrivalDate( shipment.getExpectedArrivalDate() );
        shipmentEntity.setId( shipment.getId() );
        shipmentEntity.setStatus( shipment.getStatus() );

        return shipmentEntity;
    }

    private String shipmentInputOriginCode(ShipmentInput shipmentInput) {
        City origin = shipmentInput.getOrigin();
        if ( origin == null ) {
            return null;
        }
        return origin.getCode();
    }

    private String shipmentInputDestinationCode(ShipmentInput shipmentInput) {
        City destination = shipmentInput.getDestination();
        if ( destination == null ) {
            return null;
        }
        return destination.getCode();
    }

    private String shipmentOriginCode(Shipment shipment) {
        City origin = shipment.getOrigin();
        if ( origin == null ) {
            return null;
        }
        return origin.getCode();
    }

    private String shipmentDestinationCode(Shipment shipment) {
        City destination = shipment.getDestination();
        if ( destination == null ) {
            return null;
        }
        return destination.getCode();
    }
}
