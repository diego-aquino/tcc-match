package com.ufcg.psoft.tccmatch.services.users.professors;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidGuidanceQuotaException extends BadRequestApiException {

  public InvalidGuidanceQuotaException() {
    super(message());
  }

  public static String message() {
    return "Invalid guidance quota.";
  }
}
