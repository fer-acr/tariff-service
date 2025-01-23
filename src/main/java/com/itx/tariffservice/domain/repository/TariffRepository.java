package com.itx.tariffservice.domain.repository;

import com.itx.tariffservice.domain.model.Tariff;
import java.time.Instant;
import java.util.List;

public interface TariffRepository {
  List<Tariff> findByProductIdBrandIdInDateSortedByPriorityDesc(
      Integer productId, Integer brandId, Instant queryDateTime);
}
