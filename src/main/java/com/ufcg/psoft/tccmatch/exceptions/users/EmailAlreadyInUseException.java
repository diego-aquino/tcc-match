package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.ConflictApiException;

public class EmailAlreadyInUseException extends ConflictApiException {

  public EmailAlreadyInUseException() {
    super(message());
  }

  public static String message() {
    return "Email already in use.";
  }
}
