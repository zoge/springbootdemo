package com.gere.demo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MaxAddressesExceededException extends RuntimeException {
  public MaxAddressesExceededException(Integer personId) {
    super("For a person only 2 address allowed (personId=" + personId + ")");
  }
}