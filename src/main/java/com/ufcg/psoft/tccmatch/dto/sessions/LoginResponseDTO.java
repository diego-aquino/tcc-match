package com.ufcg.psoft.tccmatch.dto.sessions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDTO {

  private String token;

  public LoginResponseDTO(@JsonProperty("token") String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
