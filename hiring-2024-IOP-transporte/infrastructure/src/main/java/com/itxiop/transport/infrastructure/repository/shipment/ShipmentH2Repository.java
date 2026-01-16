package com.itxiop.transport.infrastructure.repository.shipment;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import com.itxiop.transport.domain.shipment.repository.ShipmentRepositoryPort;
import com.itxiop.transport.domain.vo.ShipmentStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p> Implementation of {@link ShipmentRepositoryPort} with H2 database </p>
 */
@Repository
public interface ShipmentH2Repository extends JpaRepository<ShipmentEntity, UUID> {


  @Query(name = "Shipment.findShipmentDetails", nativeQuery = true)
  public List<Object> findShipmentDetails(@Param("shipmentId") UUID shipmentId);

  @Modifying
  @Transactional
  @Query("delete from ShipmentEntity s where s.status <> :pending")
  int deleteProcessed(@Param("pending") ShipmentStatusEnum pending);
}
