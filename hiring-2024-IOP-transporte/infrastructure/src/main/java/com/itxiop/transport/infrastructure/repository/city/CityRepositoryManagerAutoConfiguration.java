package com.itxiop.transport.infrastructure.repository.city;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Slf4j
public class CityRepositoryManagerAutoConfiguration {
    // DO NOT REMOVE ME FOR TASK 6
    @Bean
    CityRepositoryFileAdapter cityRepositoryFileAdapter() {
        return new CityRepositoryFileAdapter();
    }

    @Bean
    @ConditionalOnBean(CityH2Repository.class)
    CityRepositoryH2Adapter cityRepositoryH2Adapter(CityH2Repository cityH2Repository, CityEntityMapper cityEntityMapper) {
        return new CityRepositoryH2Adapter(cityH2Repository, cityEntityMapper);
    }
}
