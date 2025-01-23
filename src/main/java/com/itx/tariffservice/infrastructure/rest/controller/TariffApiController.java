package com.itx.tariffservice.infrastructure.rest.controller;

import java.time.OffsetDateTime;
import org.openapitools.api.TariffApi;
import org.openapitools.model.TariffResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TariffApiController implements TariffApi {
  @Override
  public ResponseEntity<TariffResponse> getTariff(
      Integer productId, Integer brandId, OffsetDateTime applicationDatetime) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
