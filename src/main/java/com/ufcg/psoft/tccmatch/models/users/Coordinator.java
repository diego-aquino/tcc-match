package com.ufcg.psoft.tccmatch.models.users;

import javax.persistence.Entity;

@Entity
public class Coordinator extends User {

  protected Coordinator() {
    super(Type.COORDINATOR);
  }

  public Coordinator(String email, String encodedPassword, String name) {
    super(Type.COORDINATOR, email, encodedPassword, name);
  }
}
