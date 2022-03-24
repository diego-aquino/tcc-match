package com.ufcg.psoft.tccmatch.services.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceProblem.CategoryIsNotProvidedException;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;

import org.springframework.stereotype.Service;

@Service
public class TCCGuidanceProblemValidator {
  public Category validateCategory(String category) {
    boolean isValid = category != null && !category.isBlank();
    if (isValid)
      return Category.valueOf(category.toUpperCase());
    throw new CategoryIsNotProvidedException();
  }
}
