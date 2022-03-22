package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest.Status;

public class TCCGuidanceRequestResponseDTO {

  private Long id;
  private Status status;
  private String message;
  private Long createdBy;
  private Long tccSubject;
  private Long requestedTo;

  public TCCGuidanceRequestResponseDTO(TCCGuidanceRequest tccGuidanceRequest) {
    this.id = tccGuidanceRequest.getId();
    this.status = tccGuidanceRequest.getStatus();
    this.message = tccGuidanceRequest.getMessage();
    this.createdBy = tccGuidanceRequest.getCreatedBy().getId();
    this.requestedTo = tccGuidanceRequest.getRequestedTo().getId();
    this.tccSubject = tccGuidanceRequest.getTccSubject().getId();
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
