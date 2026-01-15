package com.itxiop.transport.domain.city.repository;

import com.itxiop.transport.domain.entities.City;
import com.itxiop.transport.domain.exceptions.ResourceNotFoundException;

public interface CityRepositoryPort {

  /**
   * Find cities by city code
   * @param code City code to search
   */
  City findByCityCode(String code) throws ResourceNotFoundException;

}
