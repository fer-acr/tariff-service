package com.itx.tariffservice.domain.repository;

import com.itx.tariffservice.domain.model.Tariff;
import java.time.Instant;
import java.util.List;

public interface TariffRepository {
  /**
   * Find the list of tariff that could be applied to a product in a brand in a required datetime.
   * The list is always sorted based in reversed priority (major to minor).
   *
   * @param productId the product identifier
   * @param brandId the brand identifier
   * @param queryDateTime datetime to apply the tariff
   * @return reserve-sorted tariff list, sorted by priority
   */
  List<Tariff> findByProductIdBrandIdInDateSortedByPriorityDesc(
      Integer productId, Integer brandId, Instant queryDateTime);
}
