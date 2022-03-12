package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

public class CreateTCCGuidanceRequestRequestDTO {

  private String message;

  private long tccSubjectId;

  public CreateTCCGuidanceRequestRequestDTO(long tccSubjectId, String message) {
    this.message = message;
    this.tccSubjectId = tccSubjectId;
  }

  public String getMessage() {
    return message;
  }

  public long getTccSubjectId() {
    return tccSubjectId;
  }
}
