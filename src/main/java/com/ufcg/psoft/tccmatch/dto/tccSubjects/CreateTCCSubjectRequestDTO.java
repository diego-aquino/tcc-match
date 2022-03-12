package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import java.util.Set;

public class CreateTCCSubjectRequestDTO {

  private String title;
  private String description;
  private String status;
  private Set<String> fieldsOfStudy; //Trocar String por FieldOfStudy

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Set<String> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public String getStatus() {
    return status;
  }
}
