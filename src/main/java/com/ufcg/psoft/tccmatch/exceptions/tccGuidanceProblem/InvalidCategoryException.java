package com.ufcg.psoft.tccmatch.exceptions.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class InvalidCategoryException extends BadRequestApiException {

  public InvalidCategoryException() {
    super(message());
  }

  public static String message() {
    return "Category is invalid or was not provided.";
  }
}
