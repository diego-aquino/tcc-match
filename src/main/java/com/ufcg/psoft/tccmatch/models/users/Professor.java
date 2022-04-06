package com.ufcg.psoft.tccmatch.models.users;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Professor extends User {

  private static final Type TYPE = Type.PROFESSOR;
  public static final int DEFAULT_GUIDANCE_QUOTA = 0;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> laboratories;

  private int guidanceQuota;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<FieldOfStudy> fields;

  protected Professor() {
    super(TYPE);
  }

  public Professor(String email, String encodedPassword, String name, Set<String> laboratories) {
    this(email, encodedPassword, name, laboratories, DEFAULT_GUIDANCE_QUOTA);
  }

  public Professor(
    String email,
    String encodedPassword,
    String name,
    Set<String> laboratories,
    int guidanceQuota
  ) {
    super(TYPE, email, encodedPassword, name);
    this.laboratories = laboratories;
    this.guidanceQuota = guidanceQuota;
    this.fields = new HashSet<>();
  }

  public Set<String> getLaboratories() {
    return laboratories;
  }

  public void setLaboratories(Set<String> laboratories) {
    this.laboratories = laboratories;
  }

  public int getGuidanceQuota() {
    return guidanceQuota;
  }

  public void setGuidanceQuota(int guidanceQuota) {
    this.guidanceQuota = guidanceQuota;
  }

  public Set<FieldOfStudy> getFields() {
    return this.fields;
  }

  public void addField(FieldOfStudy fieldOfStudy) {
    fields.add(fieldOfStudy);
  }
}
