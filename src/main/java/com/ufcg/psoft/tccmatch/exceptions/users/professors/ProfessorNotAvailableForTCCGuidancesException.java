package com.ufcg.psoft.tccmatch.exceptions.users.professors;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class ProfessorNotAvailableForTCCGuidancesException extends BadRequestApiException {

  public ProfessorNotAvailableForTCCGuidancesException() {
    super(message());
  }

  private static String message() {
    return "Professor not available for TCC guidances at the moment.";
  }
}
