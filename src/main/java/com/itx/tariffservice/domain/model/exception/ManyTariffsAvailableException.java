package com.itx.tariffservice.domain.model.exception;

public class ManyTariffsAvailableException extends RuntimeException {
  public ManyTariffsAvailableException(String message) {
    super(message);
  }
}
