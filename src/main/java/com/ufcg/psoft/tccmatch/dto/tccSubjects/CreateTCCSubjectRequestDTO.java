package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import java.util.Set;

public class CreateTCCSubjectRequestDTO {

  private String title;
  private String description;
  private String status;
  private Set<Long> fieldsOfStudy;

  public CreateTCCSubjectRequestDTO(
    String title,
    String description,
    String status,
    Set<Long> fieldsOfStudy
  ) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.fieldsOfStudy = fieldsOfStudy;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Set<Long> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public String getStatus() {
    return status;
  }
}
