package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import java.util.List;

public class CreateTCCSubjectResponseDTO {

  private Long id;
  private String title;
  private String description;
  private String status;
  private List<FieldOfStudy> fieldsOfStudy;
  private Long createdBy;

  public CreateTCCSubjectResponseDTO(TCCSubject tccSubject) {
    this.id = tccSubject.getId();
    this.title = tccSubject.getTitle();
    this.description = tccSubject.getDescription();
    this.status = tccSubject.getStatus();
    this.fieldsOfStudy = List.copyOf(tccSubject.getFieldsOfStudy());
    this.createdBy = tccSubject.getCreatedBy().getId();
  }

  public Long getId() {
    return id;
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

  public List<FieldOfStudy> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public Long getCreatedBy() {
    return createdBy;
  }
}
