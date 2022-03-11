package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidUserEmailException extends BadRequestApiException {

  public InvalidUserEmailException() {
    super("Invalid email.");
  }
}
