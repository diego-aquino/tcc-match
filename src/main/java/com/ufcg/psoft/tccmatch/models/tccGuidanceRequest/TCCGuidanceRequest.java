package com.ufcg.psoft.tccmatch.models.tccGuidanceRequest;

import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TCCGuidanceRequest {

  public static enum Status {
    PENDING,
    DENIED,
    APPROVED,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Status status;
  private String message;

  @ManyToOne
  private Student createdBy;

  @ManyToOne
  private TCCSubject tccSubject;

  @ManyToOne
  private Professor requestedTo;

  public TCCGuidanceRequest() {}

  public TCCGuidanceRequest(Student createdBy, Professor requestedTo, TCCSubject tccSubject) {
    this.status = Status.PENDING;
    this.message = "";
    this.createdBy = createdBy;
    this.requestedTo = requestedTo;
    this.tccSubject = tccSubject;
  }

  public Long getId() {
    return id;
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

  public void setMessage(String message) {
    this.message = message;
  }

  public Student getCreatedBy() {
    return createdBy;
  }

  public Professor getRequestedTo() {
    return requestedTo;
  }

  public TCCSubject getTccSubject() {
    return tccSubject;
  }
}
