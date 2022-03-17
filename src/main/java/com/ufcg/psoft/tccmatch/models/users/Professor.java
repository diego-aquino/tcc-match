package com.ufcg.psoft.tccmatch.models.users;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
public class Professor extends User {

  public static final int DEFAULT_GUIDANCE_QUOTA = 0;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> laboratories;

  private int guidanceQuota;

  protected Professor() {
    super(Type.PROFESSOR);
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
    super(Type.PROFESSOR, email, encodedPassword, name);
    this.laboratories = laboratories;
    this.guidanceQuota = guidanceQuota;
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
}
