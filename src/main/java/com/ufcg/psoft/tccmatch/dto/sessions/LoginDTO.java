package com.ufcg.psoft.tccmatch.dto.sessions;

public class LoginDTO {

  private String email;
  private String password;

  public LoginDTO(String email, String password) {
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
