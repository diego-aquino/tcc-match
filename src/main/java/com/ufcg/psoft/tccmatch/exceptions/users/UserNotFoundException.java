package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public class UserNotFoundException extends NotFoundApiException {

  public UserNotFoundException() {
    super(createMessage());
  }

  public static String createMessage() {
    return "User not found.";
  }
}
