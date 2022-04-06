package com.ufcg.psoft.tccmatch.dto.users;

import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.models.users.User.Type;

public class UserResponseDTO {

  private Long id;
  private Type type;
  private String email;
  private String name;

  public UserResponseDTO(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.type = user.getType();
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }
}
