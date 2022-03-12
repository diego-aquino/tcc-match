package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest.Status;
import com.ufcg.psoft.tccmatch.models.users.User;

public class CreateTCCGuidanceRequestResponseDTO {

  private Status status;
  private String message;
  private User createdBy; //Change to Student
  private User requestedTo; //Change to Professor

  public CreateTCCGuidanceRequestResponseDTO(TCCGuidanceRequest ttcGuidanceRequest) {
    this.status = ttcGuidanceRequest.getStatus();
    this.message = ttcGuidanceRequest.getMessage();
    this.createdBy = ttcGuidanceRequest.getCreatedBy();
    this.requestedTo = ttcGuidanceRequest.getRequestedTo();
  }

  public Status getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public User getRequestedTo() {
    return requestedTo;
  }
}
