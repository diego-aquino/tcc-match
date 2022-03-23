package com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;

public class TCCGuidanceRequestNotFound extends NotFoundApiException {

  public TCCGuidanceRequestNotFound() {
    super(message());
  }

  public static String message() {
    return "TCC Guidance request not found.";
  }
}
