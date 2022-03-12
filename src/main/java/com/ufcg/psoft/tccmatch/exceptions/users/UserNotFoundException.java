package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public class UserNotFoundException extends NotFoundApiException {

  public UserNotFoundException() {
    super(message());
  }

  public static String message() {
    return "User not found.";
  }
}
