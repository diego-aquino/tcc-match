package com.ufcg.psoft.tccmatch.models.users;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Student extends User {

  private static final Type TYPE = Type.STUDENT;

  private String registryNumber;
  private String completionPeriod;

  protected Student() {
    super(TYPE);
  }

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<FieldOfStudy> fields;

  public Student(
    String email,
    String encodedPassword,
    String name,
    String registryNumber,
    String completionPeriod
  ) {
    super(TYPE, email, encodedPassword, name);
    this.registryNumber = registryNumber;
    this.completionPeriod = completionPeriod;
    this.fields = new HashSet<>();
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

  public Set<FieldOfStudy> getFields() {
    return this.fields;
  }

  public void addField(FieldOfStudy fieldOfStudy) {
    fields.add(fieldOfStudy);
  }
}
