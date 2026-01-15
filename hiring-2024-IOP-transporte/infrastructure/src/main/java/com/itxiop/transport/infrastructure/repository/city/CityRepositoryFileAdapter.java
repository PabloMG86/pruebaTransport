package com.itxiop.transport.infrastructure.repository.city;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.entities.City;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
public class CityRepositoryFileAdapter implements CityRepositoryPort, InitializingBean {

  private final Map<String, City> citiesMap = new HashMap<>();

  @Override
  public void afterPropertiesSet() throws Exception {
    log.trace("CityRepositoryAdapter. File version initialized");
    readCityHandlings();
  }

  private void readCityHandlings() throws IOException {
    List<City> cities = new ObjectMapper().readValue(new ClassPathResource("cities.json").getFile(), new TypeReference<List<City>>() {
    });
    cities.forEach(city -> citiesMap.put(city.getCode(), city));
  }

  @Override
  public City findByCityCode(String code) {
    return citiesMap.get(code);
  }
}
