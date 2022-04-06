package com.ufcg.psoft.tccmatch.models.users;

import javax.persistence.Entity;

@Entity
public class Coordinator extends User {

  private static final Type TYPE = Type.COORDINATOR;

  protected Coordinator() {
    super(TYPE);
  }

  public Coordinator(String email, String encodedPassword, String name) {
    super(TYPE, email, encodedPassword, name);
  }
}
