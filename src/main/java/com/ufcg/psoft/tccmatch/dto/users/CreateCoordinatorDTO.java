package com.ufcg.psoft.tccmatch.dto.users;

public class CreateCoordinatorDTO {

  private String email;
  private String password;

  public CreateCoordinatorDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
