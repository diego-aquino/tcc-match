package com.ufcg.psoft.tccmatch.dto.users;

import java.util.Optional;

public class UpdateStudentDTO {

  private Optional<String> email;
  private Optional<String> name;
  private Optional<String> completionPeriod;

  public UpdateStudentDTO(
    Optional<String> email,
    Optional<String> name,
    Optional<String> completionPeriod
  ) {
    this.email = email;
    this.name = name;
    this.completionPeriod = completionPeriod;
  }

  public Optional<String> getEmail() {
    return email;
  }

  public Optional<String> getName() {
    return name;
  }

  public Optional<String> getCompletionPeriod() {
    return completionPeriod;
  }
}
