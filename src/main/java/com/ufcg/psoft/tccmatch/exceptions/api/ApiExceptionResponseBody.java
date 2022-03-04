package com.ufcg.psoft.tccmatch.exceptions.api;

public class ApiExceptionResponseBody {

  private String message;

  public ApiExceptionResponseBody(ApiException exception) {
    this.message = exception.getMessage();
  }

  public String getMessage() {
    return message;
  }
}
