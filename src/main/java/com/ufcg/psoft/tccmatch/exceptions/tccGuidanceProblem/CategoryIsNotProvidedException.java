package com.ufcg.psoft.tccmatch.exceptions.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;

public class CategoryIsNotProvidedException extends BadRequestApiException {

  public CategoryIsNotProvidedException() {
    super(message());
  }

  public static String message() {
    return "Category is not provided.";
  }
}
