package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

public class CreateTCCGuidanceProblemDTO {
  private String category;
  private String description;
  private Long userWhoCreatedId;

  public CreateTCCGuidanceProblemDTO(String category, String description, Long userWhoCreatedId) {
    this.category = category;
    this.description = description;
    this.userWhoCreatedId = userWhoCreatedId;
  }

  public String getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public Long getUserWhoCreatedId() {
    return userWhoCreatedId;
  }
}
