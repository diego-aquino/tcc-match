package com.ufcg.psoft.tccmatch.exceptions.users.students;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidPeriodException extends BadRequestApiException {

  public InvalidPeriodException() {
    super(message());
  }

  public static String message() {
    return "Invalid period.";
  }
}
