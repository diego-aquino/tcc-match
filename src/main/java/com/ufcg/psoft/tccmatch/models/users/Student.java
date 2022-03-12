package com.ufcg.psoft.tccmatch.models.users;

import javax.persistence.Entity;

@Entity
public class Student extends User {

  private String registryNumber;
  private String completionPeriod;

  protected Student() {
    super(Type.STUDENT);
  }

  public Student(
    String email,
    String encodedPassword,
    String name,
    String registryNumber,
    String completionPeriod
  ) {
    super(Type.STUDENT, email, encodedPassword, name);
    this.registryNumber = registryNumber;
    this.completionPeriod = completionPeriod;
  }

  public String getRegistryNumber() {
    return registryNumber;
  }

  public void setRegistryNumber(String registryNumber) {
    this.registryNumber = registryNumber;
  }

  public String getCompletionPeriod() {
    return completionPeriod;
  }

  public void setCompletionPeriod(String completionPeriod) {
    this.completionPeriod = completionPeriod;
  }
}
