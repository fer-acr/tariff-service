package com.itx.tariffservice.infrastructure.persistence.h2.repository;

import com.itx.tariffservice.infrastructure.persistence.h2.model.TariffDBO;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TariffH2JPARepository extends JpaRepository<TariffDBO, Long> {
  @Query(
      "select t from TariffDBO t where t.productId = :productId and t.brandId = :brandId and :queryDateTime between t.startDate and t.endDate order by t.priority desc")
  List<TariffDBO> findByProductIdAndBrandIdAndInPeriodWitHighestPriority(
      Integer productId, Integer brandId, Instant queryDateTime);
}
