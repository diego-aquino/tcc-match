package com.ufcg.psoft.tccmatch.exceptions.tccSubjects;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public class TCCSubjectNotFoundException extends NotFoundApiException {

  public TCCSubjectNotFoundException() {
    super(message());
  }

  public static String message() {
    return "TCC Subject not found.";
  }
}
