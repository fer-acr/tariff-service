package com.itx.tariffservice.domain.model.exception;

public class TariffNotFoundException extends RuntimeException {
  public TariffNotFoundException(String message) {
    super(message);
  }
}
