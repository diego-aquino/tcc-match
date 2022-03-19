package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;

public class TCCGuidanceProblemResponseDTO {
  private Long id;
  private Category category;
  private String description;
  private Long userWhoCreatedId;

  public TCCGuidanceProblemResponseDTO(TCCGuidanceProblem tccGuidanceProblem) {
    this.id = tccGuidanceProblem.getId();
    this.category = tccGuidanceProblem.getCategory();
    this.description = tccGuidanceProblem.getDescription();
    this.userWhoCreatedId = tccGuidanceProblem.getCreatedBy().getId();
  }

  public Long getId() {
    return id;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public Long getUserWhoCreatedId() {
    return userWhoCreatedId;
  }
}
