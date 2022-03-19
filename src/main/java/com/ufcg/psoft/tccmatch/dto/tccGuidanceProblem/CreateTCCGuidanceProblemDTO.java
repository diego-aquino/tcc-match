package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

public class CreateTCCGuidanceProblemDTO {
  private String category;
  private String description;
  private Long tccGuidanceId;

  public CreateTCCGuidanceProblemDTO(String category, String description, Long tccGuidanceId) {
    this.category = category;
    this.description = description;
    this.tccGuidanceId = tccGuidanceId;
  }

  public String getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public Long getTCCGuidanceId() {
    return tccGuidanceId;
  }
}
