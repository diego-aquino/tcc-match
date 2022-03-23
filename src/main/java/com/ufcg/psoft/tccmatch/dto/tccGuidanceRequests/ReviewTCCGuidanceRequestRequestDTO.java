package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

public class ReviewTCCGuidanceRequestRequestDTO {

  private boolean isApproved;

  private String message;

  public ReviewTCCGuidanceRequestRequestDTO(boolean isApproved, String message) {
    this.isApproved = isApproved;
    this.message = message;
  }

  public boolean getIsApproved() {
    return isApproved;
  }

  public String getMessage() {
    return message;
  }
}
