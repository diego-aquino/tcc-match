package com.ufcg.psoft.tccmatch.dto.users;

public class CreateStudentDTO {

  private String email;
  private String password;
  private String name;
  private String registryNumber;
  private String completionPeriod;

  public CreateStudentDTO(
    String email,
    String password,
    String name,
    String registryNumber,
    String completionPeriod
  ) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.registryNumber = registryNumber;
    this.completionPeriod = completionPeriod;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
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
