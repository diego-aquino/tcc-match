package com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class TCCGuidanceRequestUnauthorizedProfessor extends BadRequestApiException {

  public TCCGuidanceRequestUnauthorizedProfessor() {
    super(message());
  }

  public static String message() {
    return "You can't review this TCC Guidance Request.";
  }
}
