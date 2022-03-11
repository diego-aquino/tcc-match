package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class UserPasswordTooShortException extends BadRequestApiException {

  public UserPasswordTooShortException() {
    super("Password too short.");
  }
}
