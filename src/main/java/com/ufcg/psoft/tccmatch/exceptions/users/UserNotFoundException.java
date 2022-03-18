package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public abstract class UserNotFoundException extends NotFoundApiException {

  protected UserNotFoundException(String message) {
    super(message);
  }
}
