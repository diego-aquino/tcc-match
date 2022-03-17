package com.ufcg.psoft.tccmatch.exceptions.users.students;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidRegistryNumberException extends BadRequestApiException {

  public InvalidRegistryNumberException() {
    super(message());
  }

  public static String message() {
    return "Invalid registry number.";
  }
}
