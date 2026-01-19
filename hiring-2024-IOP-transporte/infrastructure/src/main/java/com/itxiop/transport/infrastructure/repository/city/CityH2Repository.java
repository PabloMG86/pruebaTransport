package com.itxiop.transport.infrastructure.repository.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para ciudades en H2.
 */
@Repository
public interface CityH2Repository extends JpaRepository<CityEntity, String> {
}
