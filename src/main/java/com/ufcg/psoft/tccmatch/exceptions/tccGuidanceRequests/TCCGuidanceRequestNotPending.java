package com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class TCCGuidanceRequestNotPending extends BadRequestApiException {

  public TCCGuidanceRequestNotPending() {
    super(message());
  }

  public static String message() {
    return "Given TCC Guidance Request is not pending.";
  }
}
