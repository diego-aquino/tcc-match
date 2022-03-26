package com.ufcg.psoft.tccmatch.exceptions.tccSubjects;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidTCCSubjectException extends BadRequestApiException {

  public InvalidTCCSubjectException() {
    super(message());
  }

  public static String message() {
    return "Invalid TCC Subject for this operation.";
  }
}
