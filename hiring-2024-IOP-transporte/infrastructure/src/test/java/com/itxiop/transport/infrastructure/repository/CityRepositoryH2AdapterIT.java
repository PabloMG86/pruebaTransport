package com.itxiop.transport.infrastructure.repository;

import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.exceptions.ResourceNotFoundException;
import com.itxiop.transport.infrastructure.TestInfrastructureApplication;
import com.itxiop.transport.infrastructure.repository.city.CityRepositoryManagerAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.defer-datasource-initialization=true"
})
@Import({TestInfrastructureApplication.class, CityRepositoryManagerAutoConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CityRepositoryH2AdapterIT {

  @Autowired
  private CityRepositoryPort cityRepositoryPort;

  @Test
  void findsCityFromDatabase() throws ResourceNotFoundException {
    City city = cityRepositoryPort.findByCityCode("MAD");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("Madrid");
    assertThat(city.getHandlingCost()).isEqualByComparingTo("1");
  }
}
