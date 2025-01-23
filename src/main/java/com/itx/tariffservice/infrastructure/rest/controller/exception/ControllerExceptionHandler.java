package com.itx.tariffservice.infrastructure.rest.controller.exception;

import com.itx.tariffservice.domain.model.exception.ManyTariffsAvailableException;
import com.itx.tariffservice.domain.model.exception.TariffNotFoundException;
import org.openapitools.model.TariffErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(TariffNotFoundException.class)
  public ResponseEntity<TariffErrorResponse> handleException(TariffNotFoundException e) {
    return new ResponseEntity<>(composeError(e, "Tariff not found"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ManyTariffsAvailableException.class)
  public ResponseEntity<TariffErrorResponse> handleException(ManyTariffsAvailableException e) {
    return new ResponseEntity<>(composeError(e, "Many tariffs available"), HttpStatus.CONFLICT);
  }

  private TariffErrorResponse composeError(Exception e, String title) {
    TariffErrorResponse errorResponse = new TariffErrorResponse();
    errorResponse.title(title);
    errorResponse.detail(e.getMessage());
    return errorResponse;
  }
}
