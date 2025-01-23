package com.itx.tariffservice.application;

import com.itx.tariffservice.domain.model.Tariff;
import com.itx.tariffservice.domain.service.TariffService;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class FindActiveTariff {
  private final TariffService tariffService;

  public FindActiveTariff(TariffService tariffService) {
    this.tariffService = tariffService;
  }

  public Tariff apply(Integer productId, Integer brandId, Instant applicationInstant) {
    return tariffService.findActiveTariff(productId, brandId, applicationInstant);
  }
}
