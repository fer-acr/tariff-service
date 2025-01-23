package com.itx.tariffservice.domain.service;

import com.itx.tariffservice.domain.model.Tariff;
import com.itx.tariffservice.domain.model.exception.ManyTariffsAvailableException;
import com.itx.tariffservice.domain.model.exception.TariffNotFoundException;
import com.itx.tariffservice.domain.repository.TariffRepository;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TariffService {
  private final TariffRepository tariffRepository;

  public TariffService(TariffRepository tariffRepository) {
    this.tariffRepository = tariffRepository;
  }

  /**
   * Retrieve the maximum priority tariff to be applied to the given product in a concrete datetime
   *
   * @param productId product identifier
   * @param brandId brand identifier
   * @param applicationDatetime datetime when the tariff if going to be applied
   * @return the tariff to be applied to the product
   */
  public Tariff findActiveTariff(Integer productId, Integer brandId, Instant applicationDatetime) {
    var tariffs =
        tariffRepository.findByProductIdBrandIdInDateSortedByPriorityDesc(
            productId, brandId, applicationDatetime);
    if (CollectionUtils.isEmpty(tariffs)) {
      throw new TariffNotFoundException(
          String.format(
              "Tariff not found for product %s brand %s and datetime %s",
              productId, brandId, applicationDatetime.atZone(ZoneOffset.UTC)));
    }

    Tariff highestPriorityTariff = tariffs.get(0);

    if (tariffs.stream()
            .filter(t -> Objects.equals(t.getPriority(), highestPriorityTariff.getPriority()))
            .count()
        > 1) {
      throw new ManyTariffsAvailableException(
          String.format(
              "So many tariffs found for product %s brand %s and datetime %s",
              productId, brandId, applicationDatetime.atZone(ZoneOffset.UTC)));
    }

    return highestPriorityTariff;
  }
}
