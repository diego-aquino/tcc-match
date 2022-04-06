package com.ufcg.psoft.tccmatch.dto.users;

import java.util.Set;

public class CreateProfessorDTO {

  private String email;
  private String password;
  private String name;
  private Set<String> laboratories;

  public CreateProfessorDTO(String email, String password, String name, Set<String> laboratories) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.laboratories = laboratories;
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

  public Set<String> getLaboratories() {
    return laboratories;
  }
}
