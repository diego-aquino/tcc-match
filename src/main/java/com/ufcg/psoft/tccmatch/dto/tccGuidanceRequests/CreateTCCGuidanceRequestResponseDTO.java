package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest.Status;

public class CreateTCCGuidanceRequestResponseDTO {

  private Long id;
  private Status status;
  private String message;
  private Long createdBy;
  private Long tccSubject;
  private Long requestedTo;

  public CreateTCCGuidanceRequestResponseDTO(TCCGuidanceRequest ttcGuidanceRequest) {
    this.id = ttcGuidanceRequest.getId();
    this.status = ttcGuidanceRequest.getStatus();
    this.message = ttcGuidanceRequest.getMessage();
    this.createdBy = ttcGuidanceRequest.getCreatedBy().getId();
    this.requestedTo = ttcGuidanceRequest.getRequestedTo().getId();
    this.tccSubject = ttcGuidanceRequest.getTccSubject().getId();
  }

  public Long getId() {
    return id;
  }

  public Long getTccSubject() {
    return tccSubject;
  }

  public Status getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public Long getRequestedTo() {
    return requestedTo;
  }
}
