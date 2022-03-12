package com.ufcg.psoft.tccmatch.exceptions.users.students;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidCompletionPeriodException extends BadRequestApiException {

  public InvalidCompletionPeriodException() {
    super(message());
  }

  public static String message() {
    return "Invalid completion period.";
  }
}
