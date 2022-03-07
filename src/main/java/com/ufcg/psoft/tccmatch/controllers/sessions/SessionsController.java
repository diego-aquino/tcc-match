package com.ufcg.psoft.tccmatch.controllers.sessions;

import com.ufcg.psoft.tccmatch.dto.sessions.LoginRequestDTO;
import com.ufcg.psoft.tccmatch.dto.sessions.LoginResponseDTO;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
    );

    String token = authenticationService.generateToken(authentication);

    return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
  }

  /**
   * Temporary endpoint to check if the authentication works. If the user is not authenticated or
   * his/her token has expired, the response should be an error with code 403 (forbidden).
   */
  @GetMapping("/check")
  public ResponseEntity<String> checkAuthentication() {
    User user = authenticationService.getAuthenticatedUser();

    return new ResponseEntity<>(
      String.format("Authenticated with %s!", user.getEmail()),
      HttpStatus.OK
    );
  }
}
