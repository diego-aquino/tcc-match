package com.ufcg.psoft.tccmatch.dto.users;

import com.ufcg.psoft.tccmatch.models.users.Professor;
import java.util.List;

public class CreateProfessorResponseDTO {

  private Long id;
  private String email;
  private String name;
  private List<String> laboratories;
  private int guidanceQuota;

  public CreateProfessorResponseDTO(Professor professor) {
    this.id = professor.getId();
    this.email = professor.getEmail();
    this.name = professor.getName();
    this.laboratories = List.copyOf(professor.getLaboratories());
    this.guidanceQuota = professor.getGuidanceQuota();
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public List<String> getLaboratories() {
    return laboratories;
  }

  public int getGuidanceQuota() {
    return guidanceQuota;
  }
}
