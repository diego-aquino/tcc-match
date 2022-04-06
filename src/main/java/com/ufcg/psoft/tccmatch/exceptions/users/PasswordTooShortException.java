package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class PasswordTooShortException extends BadRequestApiException {

  public PasswordTooShortException() {
    super(message());
  }

  public static String message() {
    return "Password too short.";
  }
}
