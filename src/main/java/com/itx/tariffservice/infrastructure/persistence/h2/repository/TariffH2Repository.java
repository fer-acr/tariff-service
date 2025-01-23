package com.itx.tariffservice.infrastructure.persistence.h2.repository;

import com.itx.tariffservice.domain.model.Tariff;
import com.itx.tariffservice.domain.repository.TariffRepository;
import com.itx.tariffservice.infrastructure.persistence.h2.mapper.TariffDBOMapper;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TariffH2Repository implements TariffRepository {
  private final TariffH2JPARepository tariffH2JPARepository;
  private final TariffDBOMapper mapper;

  public TariffH2Repository(TariffH2JPARepository tariffH2JPARepository, TariffDBOMapper mapper) {
    this.tariffH2JPARepository = tariffH2JPARepository;
    this.mapper = mapper;
  }

  public List<Tariff> findByProductIdBrandIdInDateSortedByPriorityDesc(
      Integer productId, Integer brandId, Instant queryDateTime) {
    return tariffH2JPARepository
        .findByProductIdAndBrandIdAndInPeriodWitHighestPriority(productId, brandId, queryDateTime)
        .stream()
        .map(mapper)
        .toList();
  }
}
