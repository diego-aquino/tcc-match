package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TCCSubjectResponseDTO {

  private Long id;
  private String title;
  private String description;
  private String status;
  private List<FieldOfStudy> fieldsOfStudy;
  private Long createdBy;

  public TCCSubjectResponseDTO(TCCSubject tccSubject) {
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

  public static List<TCCSubjectResponseDTO> fromTCCSubjects(Set<TCCSubject> tccSubjects) {
    return Arrays.asList(
      tccSubjects.stream().map(TCCSubjectResponseDTO::new).toArray(TCCSubjectResponseDTO[]::new)
    );
  }
}
