package com.itx.tariffservice.infrastructure.rest.controller.mapper;

import com.itx.tariffservice.domain.model.Tariff;
import java.time.ZoneOffset;
import java.util.function.Function;
import org.openapitools.model.TariffResponse;
import org.springframework.stereotype.Component;

@Component
public class TariffResponseMapper implements Function<Tariff, TariffResponse> {

  @Override
  public TariffResponse apply(Tariff tariff) {
    TariffResponse tariffResponse = new TariffResponse();
    tariffResponse.productId(tariff.getProductId());
    tariffResponse.brandId(tariff.getBrandId());
    tariffResponse.tariffId(tariff.getPriceList());
    tariffResponse.price(tariff.getPrice());
    tariffResponse.currency(tariff.getCurrency());
    tariffResponse.startDatetime(tariff.getStartDate().atOffset(ZoneOffset.UTC));
    tariffResponse.endDatetime(tariff.getEndDate().atOffset(ZoneOffset.UTC));
    return tariffResponse;
  }
}
