package com.ufcg.psoft.tccmatch.dto.users;

import com.ufcg.psoft.tccmatch.models.users.User;

public class CreateUserResponseDTO {

  private Long id;
  private String email;

  public CreateUserResponseDTO(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
