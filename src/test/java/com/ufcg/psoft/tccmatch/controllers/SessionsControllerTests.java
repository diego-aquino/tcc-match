package com.ufcg.psoft.tccmatch.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.sessions.LoginRequestDTO;
import com.ufcg.psoft.tccmatch.dto.sessions.LoginResponseDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateUserRequestDTO;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class SessionsControllerTests extends IntegrationTests {

  private String userEmail = "user@email.com";
  private String userRawPassword = "12345678";

  @Autowired
  private UserService userService;

  @Test
  void validLogin() throws Exception {
    userService.createUser(new CreateUserRequestDTO(userEmail, userRawPassword));

    LoginRequestDTO loginDTO = new LoginRequestDTO(userEmail, userRawPassword);

    makeLoginRequest(loginDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.token", is(any(String.class))));
  }

  @Test
  void loginWithInvalidCredentials() throws Exception {
    userService.createUser(new CreateUserRequestDTO(userEmail, userRawPassword));

    String anotherEmail = "anotheruser@email.com";
    LoginRequestDTO loginDTOWithAnotherEmail = new LoginRequestDTO(anotherEmail, userRawPassword);

    makeLoginRequest(loginDTOWithAnotherEmail).andExpect(status().isUnauthorized());

    String anotherPassword = "11111111";
    LoginRequestDTO loginDTOWithAnotherPassword = new LoginRequestDTO(userEmail, anotherPassword);

    makeLoginRequest(loginDTOWithAnotherPassword).andExpect(status().isUnauthorized());
  }

  private ResultActions makeLoginRequest(LoginRequestDTO loginDTO) throws Exception {
    return mvc.perform(
      post("/api/sessions/login").contentType(MediaType.APPLICATION_JSON).content(toJSON(loginDTO))
    );
  }

  @Test
  void checkWithAuthenticatedUser() throws Exception {
    userService.createUser(new CreateUserRequestDTO(userEmail, userRawPassword));

    LoginRequestDTO loginDTO = new LoginRequestDTO(userEmail, userRawPassword);
    MvcResult loginResult = makeLoginRequest(loginDTO).andReturn();

    LoginResponseDTO loginResponseDTO = fromJSON(
      loginResult.getResponse().getContentAsString(),
      LoginResponseDTO.class
    );

    String token = loginResponseDTO.getToken();

    makeCheckRequest(token)
      .andExpect(status().isOk())
      .andExpect(content().string(is(String.format("Authenticated with %s!", userEmail))));
  }

  @Test
  void checkWithNonAuthenticatedUser() throws Exception {
    String invalidToken = "invalid-token";
    makeCheckRequest(invalidToken).andExpect(status().isForbidden());
  }

  private ResultActions makeCheckRequest(String token) throws Exception {
    return mvc.perform(
      get("/api/sessions/check").header("Authorization", String.format("Bearer %s", token))
    );
  }
}
