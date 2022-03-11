package com.ufcg.psoft.tccmatch.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.sessions.LoginDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateCoordinatorDTO;
import com.ufcg.psoft.tccmatch.services.users.coordinators.CoordinatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class SessionsControllerTests extends IntegrationTests {

  private String userEmail = "user@email.com";
  private String userRawPassword = "12345678";

  @Autowired
  private CoordinatorService coordinatorService;

  @Test
  void validLogin() throws Exception {
    coordinatorService.createCoordinator(new CreateCoordinatorDTO(userEmail, userRawPassword));

    LoginDTO loginDTO = new LoginDTO(userEmail, userRawPassword);

    makeLoginRequest(loginDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.token", is(any(String.class))));
  }

  @Test
  void loginWithInvalidCredentials() throws Exception {
    coordinatorService.createCoordinator(new CreateCoordinatorDTO(userEmail, userRawPassword));

    String anotherEmail = "anotheruser@email.com";
    LoginDTO loginDTOWithAnotherEmail = new LoginDTO(anotherEmail, userRawPassword);

    makeLoginRequest(loginDTOWithAnotherEmail).andExpect(status().isUnauthorized());

    String anotherPassword = "11111111";
    LoginDTO loginDTOWithAnotherPassword = new LoginDTO(userEmail, anotherPassword);

    makeLoginRequest(loginDTOWithAnotherPassword).andExpect(status().isUnauthorized());
  }

  private ResultActions makeLoginRequest(LoginDTO loginDTO) throws Exception {
    return mvc.perform(
      post("/api/sessions/login").contentType(MediaType.APPLICATION_JSON).content(toJSON(loginDTO))
    );
  }
}
