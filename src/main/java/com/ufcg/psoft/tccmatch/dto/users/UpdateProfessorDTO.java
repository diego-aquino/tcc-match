package com.ufcg.psoft.tccmatch.dto.users;

import java.util.Optional;
import java.util.Set;

public class UpdateProfessorDTO {

  private Optional<String> email;
  private Optional<String> name;
  private Optional<Set<String>> laboratories;
  private Optional<Integer> guidanceQuota;

  public UpdateProfessorDTO(
    Optional<String> email,
    Optional<String> name,
    Optional<Set<String>> laboratories,
    Optional<Integer> guidanceQuota
  ) {
    this.email = email;
    this.name = name;
    this.laboratories = laboratories;
    this.guidanceQuota = guidanceQuota;
  }

  public Optional<String> getEmail() {
    return email;
  }

  public Optional<String> getName() {
    return name;
  }

  public Optional<Set<String>> getLaboratories() {
    return laboratories;
  }

  public Optional<Integer> getGuidanceQuota() {
    return guidanceQuota;
  }
}
