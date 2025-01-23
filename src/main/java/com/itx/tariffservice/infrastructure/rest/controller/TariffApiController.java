package com.itx.tariffservice.infrastructure.rest.controller;

import com.itx.tariffservice.application.FindActiveTariff;
import com.itx.tariffservice.infrastructure.rest.controller.mapper.TariffResponseMapper;
import java.time.OffsetDateTime;
import org.openapitools.api.TariffApi;
import org.openapitools.model.TariffResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TariffApiController implements TariffApi {
  private final FindActiveTariff findActiveTariff;
  private final TariffResponseMapper mapper;

  public TariffApiController(FindActiveTariff findActiveTariff, TariffResponseMapper mapper) {
    this.findActiveTariff = findActiveTariff;
    this.mapper = mapper;
  }

  @Override
  public ResponseEntity<TariffResponse> getTariff(
      Integer productId, Integer brandId, OffsetDateTime applicationDatetime) {
    mapper.apply(findActiveTariff.apply(productId, brandId, applicationDatetime.toInstant()));
    return ResponseEntity.ok(
        mapper.apply(findActiveTariff.apply(productId, brandId, applicationDatetime.toInstant())));
  }
}
