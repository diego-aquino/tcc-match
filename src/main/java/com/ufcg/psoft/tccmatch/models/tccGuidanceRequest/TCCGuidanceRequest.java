package com.ufcg.psoft.tccmatch.models.tccGuidanceRequest;

import com.ufcg.psoft.tccmatch.models.users.User;
import javax.persistence.ManyToOne;

public class TCCGuidanceRequest {

  public enum Status {
    PENDING,
    DENIED,
    APPROVED,
  }

  private Status status;
  private String message;

  @ManyToOne
  private User createdBy; //Change to Student

  @ManyToOne
  private User requestedTo; //Change to Professor

  public TCCGuidanceRequest(String message, User createdBy, User requestedTo) {
    this.status = Status.PENDING;
    this.message = message;
    this.createdBy = createdBy;
    this.requestedTo = requestedTo;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
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
