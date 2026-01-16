package com.itxiop.transport.infrastructure.repository.city;

import com.itxiop.transport.domain.entities.City;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T08:51:27+0100",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CityEntityMapperImpl implements CityEntityMapper {

    @Override
    public City toDomainEntity(CityEntity city) {
        if ( city == null ) {
            return null;
        }

        City city1 = new City();

        city1.setCode( city.getCode() );
        city1.setName( city.getName() );
        city1.setHandlingCost( city.getHandlingCost() );

        return city1;
    }

    @Override
    public List<City> toDomainEntities(List<CityEntity> cities) {
        if ( cities == null ) {
            return null;
        }

        List<City> list = new ArrayList<City>( cities.size() );
        for ( CityEntity cityEntity : cities ) {
            list.add( toDomainEntity( cityEntity ) );
        }

        return list;
    }
}
