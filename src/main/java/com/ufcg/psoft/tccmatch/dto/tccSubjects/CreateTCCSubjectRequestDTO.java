package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import java.util.Set;

public class CreateTCCSubjectRequestDTO {

  private String title;
  private String description;
  private String status;
  private Set<FieldOfStudy> fieldsOfStudy;

  public CreateTCCSubjectRequestDTO(
    String title,
    String description,
    String status,
    Set<FieldOfStudy> fieldsOfStudy
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

  public Set<FieldOfStudy> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public String getStatus() {
    return status;
  }
}
