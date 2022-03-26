package com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class EmptyTCCGuidanceRequestReviewMessageException extends BadRequestApiException {

  public EmptyTCCGuidanceRequestReviewMessageException() {
    super(message());
  }

  public static String message() {
    return "The review message cannot be empty.";
  }
}
