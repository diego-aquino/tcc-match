package com.ufcg.psoft.tccmatch.exceptions.users.professors;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class LaboratoriesNotProvidedException extends BadRequestApiException {

  public LaboratoriesNotProvidedException() {
    super(message());
  }

  public static String message() {
    return "Laboratories not provided.";
  }
}
