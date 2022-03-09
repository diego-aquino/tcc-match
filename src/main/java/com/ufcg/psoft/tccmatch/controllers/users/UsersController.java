package com.ufcg.psoft.tccmatch.controllers.users;

import com.ufcg.psoft.tccmatch.dto.users.CreateUserRequestDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateUserResponseDTO;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  @Autowired
  private UserService usersService;

  @PostMapping("/user") // temporary endpoint (objects of the type User won't be created directly in the future)
  public ResponseEntity<CreateUserResponseDTO> createUser(
    @RequestBody CreateUserRequestDTO createUserDTO
  ) {
    User user = usersService.createUser(createUserDTO);
    return new ResponseEntity<>(new CreateUserResponseDTO(user), HttpStatus.CREATED);
  }
}
