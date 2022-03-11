package com.ufcg.psoft.tccmatch.controllers.sessions;

import com.ufcg.psoft.tccmatch.dto.sessions.LoginDTO;
import com.ufcg.psoft.tccmatch.dto.sessions.LoginResponseDTO;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
    String token = authenticationService.loginWithEmailAndPassword(
      loginDTO.getEmail(),
      loginDTO.getPassword()
    );

    return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.CREATED);
  }
}
