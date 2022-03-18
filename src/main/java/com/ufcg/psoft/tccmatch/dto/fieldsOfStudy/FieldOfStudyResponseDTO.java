package com.ufcg.psoft.tccmatch.dto.fieldsOfStudy;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;

public class FieldOfStudyResponseDTO {

  private Long id;
  private String name;

  public FieldOfStudyResponseDTO(FieldOfStudy field) {
    this.id = field.getId();
    this.name = field.getName();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
