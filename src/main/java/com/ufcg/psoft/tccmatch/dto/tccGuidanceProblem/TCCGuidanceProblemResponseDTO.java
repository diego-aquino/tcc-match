package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.dto.users.UserResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;
import com.ufcg.psoft.tccmatch.models.users.User;

public class TCCGuidanceProblemResponseDTO {
  private Long id;
  private Category category;
  private String description;
  private Long tccGuidanceId;
  private User createdBy;

  public TCCGuidanceProblemResponseDTO(TCCGuidanceProblem tccGuidanceProblem) {
    this.id = tccGuidanceProblem.getId();
    this.category = tccGuidanceProblem.getCategory();
    this.description = tccGuidanceProblem.getDescription();
    this.tccGuidanceId = tccGuidanceProblem.getTCCGuidance().getId();
    this.createdBy = tccGuidanceProblem.getCreatedBy();
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

  public Long getTCCGuidanceId() {
    return tccGuidanceId;
  }

  public UserResponseDTO getCreatedBy() {
    return new UserResponseDTO(createdBy);
  }
}
