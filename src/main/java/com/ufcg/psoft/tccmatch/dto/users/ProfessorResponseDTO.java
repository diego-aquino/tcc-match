package com.ufcg.psoft.tccmatch.dto.users;

import com.ufcg.psoft.tccmatch.models.users.Professor;
import java.util.Arrays;
import java.util.List;

public class ProfessorResponseDTO {

  private Long id;
  private String email;
  private String name;
  private List<String> laboratories;
  private int guidanceQuota;

  public ProfessorResponseDTO(Professor professor) {
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

  public static List<ProfessorResponseDTO> listProfessors(List<Professor> tccGuidances) {
    return Arrays.asList(
      tccGuidances.stream().map(ProfessorResponseDTO::new).toArray(ProfessorResponseDTO[]::new)
    );
  }
}
