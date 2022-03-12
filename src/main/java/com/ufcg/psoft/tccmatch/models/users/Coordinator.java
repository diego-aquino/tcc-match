package com.ufcg.psoft.tccmatch.models.users;

import javax.persistence.Entity;

@Entity
public class Coordinator extends User {

  protected Coordinator() {}

  public Coordinator(String email, String encodedPassword) {
    super(Type.COORDINATOR, email, encodedPassword);
  }
}
