package com.ufcg.psoft.tccmatch.exceptions.tccGuidances;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public class TCCGuidanceNotFoundException extends NotFoundApiException {
  public TCCGuidanceNotFoundException() {
    super(message());
  }

  public static String message() {
    return "TCCGuidance not found.";
  }
}
