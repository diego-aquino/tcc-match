package com.ufcg.psoft.tccmatch.exceptions.fieldsOfStudy;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public class FieldNotFoundException extends NotFoundApiException {

  public FieldNotFoundException() {
    super(message());
  }

  public static String message() {
    return "Field not found.";
  }
}
