package com.itxiop.transport.infrastructure.repository.city;

import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CityRepositoryH2Adapter implements CityRepositoryPort, InitializingBean {
  
      private final CityH2Repository cityH2Repository;
      
      private final CityEntityMapper cityEntityMapper;

      @Autowired(required = false)
      private CityRepositoryFileAdapter fallbackFileAdapter;

      @Override
      public City findByCityCode(String code) throws ResourceNotFoundException {
            // Primero intenta en BBDD
            var entity = cityH2Repository.findById(code);
            if (entity.isPresent()) {
                  return cityEntityMapper.toDomainEntity(entity.get());
            }
            // Fallback al adaptador de fichero si está presente
            if (fallbackFileAdapter != null) {
                  try {
                        return fallbackFileAdapter.findByCityCode(code);
                  } catch (ResourceNotFoundException ignored) {
                        // ignoramos para lanzar la excepción estándar abajo
                  }
            }
            throw new ResourceNotFoundException("City " + code + " not found");
      }

      @Override
      public void afterPropertiesSet() throws Exception {
            log.trace("CityRepositoryAdapter. H2 version initialized");
      }
}
