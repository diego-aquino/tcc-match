package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class EmptyUserNameException extends BadRequestApiException {

  public EmptyUserNameException() {
    super(message());
  }

  public static String message() {
    return "User name is empty.";
  }
}
