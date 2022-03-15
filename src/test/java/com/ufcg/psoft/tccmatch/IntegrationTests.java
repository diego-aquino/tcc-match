package com.ufcg.psoft.tccmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class IntegrationTests {

  @Value("${users.default-coordinator.email}")
  private String defaultCoordinatorEmail;

  @Value("${users.default-coordinator.password}")
  private String defaultCoordinatorPassword;

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  private AuthenticationService authenticationService;

  protected String toJSON(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  protected <Response> Response fromJSON(String value, Class<Response> valueType)
    throws JsonProcessingException {
    return objectMapper.readValue(value, valueType);
  }

  protected String loginProgrammaticallyWithDefaultCoordinator() {
    return loginProgrammatically(defaultCoordinatorEmail, defaultCoordinatorPassword);
  }

  protected String loginProgrammatically(String email, String password) {
    String token = authenticationService.loginWithEmailAndPassword(email, password);
    return token;
  }

  protected MockHttpServletRequestBuilder authenticated(
    MockHttpServletRequestBuilder builder,
    String token
  ) {
    return builder.header("Authorization", String.format("Bearer %s", token));
  }
}
