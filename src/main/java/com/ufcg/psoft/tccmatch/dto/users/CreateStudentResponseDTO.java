package com.ufcg.psoft.tccmatch.dto.users;

import com.ufcg.psoft.tccmatch.models.users.Student;

public class CreateStudentResponseDTO {

  private Long id;
  private String email;
  private String name;
  private String registryNumber;
  private String completionPeriod;

  public CreateStudentResponseDTO(Student student) {
    this.id = student.getId();
    this.email = student.getEmail();
    this.name = student.getName();
    this.registryNumber = student.getRegistryNumber();
    this.completionPeriod = student.getCompletionPeriod();
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

  public String getRegistryNumber() {
    return registryNumber;
  }

  public String getCompletionPeriod() {
    return completionPeriod;
  }
}
