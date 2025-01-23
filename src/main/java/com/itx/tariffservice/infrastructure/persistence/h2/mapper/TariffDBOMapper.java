package com.itx.tariffservice.infrastructure.persistence.h2.mapper;

import com.itx.tariffservice.domain.model.Tariff;
import com.itx.tariffservice.infrastructure.persistence.h2.model.TariffDBO;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TariffDBOMapper implements Function<TariffDBO, Tariff> {

  @Override
  public Tariff apply(TariffDBO tariffDBO) {
    Tariff tariff = new Tariff();
    tariff.setProductId(tariffDBO.getProductId());
    tariff.setBrandId(tariffDBO.getBrandId());
    tariff.setStartDate(tariffDBO.getStartDate());
    tariff.setEndDate(tariffDBO.getEndDate());
    tariff.setPriceList(tariffDBO.getPriceList());
    tariff.setPriority(tariffDBO.getPriority());
    tariff.setPrice(tariffDBO.getPrice());
    tariff.setCurrency(tariffDBO.getCurrency());
    return tariff;
  }
}
