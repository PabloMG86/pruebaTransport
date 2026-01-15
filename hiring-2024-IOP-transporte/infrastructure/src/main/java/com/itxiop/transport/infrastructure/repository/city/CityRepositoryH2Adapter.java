package com.itxiop.transport.infrastructure.repository.city;

import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.entities.City;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.InitializingBean;

@AllArgsConstructor
@Slf4j
public class CityRepositoryH2Adapter implements CityRepositoryPort, InitializingBean {
  
      private final CityH2Repository cityH2Repository;
      
      private CityEntityMapper cityEntityMapper;

      @Override
      public City findByCityCode(String code) {
            // TODO #5: Implement database search
            throw new NotImplementedException("findByCode not implemented in H2 Port adapter. You may refer to CityRepositoryFileAdapter" +
                    " or is something missing here?");
      }

      @Override
      public void afterPropertiesSet() throws Exception {
            log.trace("CityRepositoryAdapter. H2 version initialized");
      }
}
