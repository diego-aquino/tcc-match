package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Set;

public class CreateTCCSubjectResponseDTO {

  private String title;
  private String description;
  private String status;
  private Set<String> fieldsOfStudy;
  private User createdBy;

  public CreateTCCSubjectResponseDTO(TCCSubject tccSubject) {
    this.title = tccSubject.getTitle();
    this.description = tccSubject.getDescription();
    this.status = tccSubject.getStatus();
    this.fieldsOfStudy = tccSubject.getFieldsOfStudy();
    this.createdBy = tccSubject.getCreatedBy();
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public Set<String> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public User getCreatedBy() {
    return createdBy;
  }
}
