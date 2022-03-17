package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidEmailApiException extends BadRequestApiException {

  public InvalidEmailApiException() {
    super(message());
  }

  public static String message() {
    return "Invalid email.";
  }
}
