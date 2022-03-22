package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class FieldsOfStudyControllerTest extends IntegrationTests {

  private String name = "field";
  private String coordinatorToken;

  @Autowired
  private FieldsOfStudyService fieldsOfStudyService;

  @BeforeEach
  void beforeEach() {
    coordinatorToken = loginWithDefaultCoordinator();
  }

  @Test
  void validFieldOfStudyCreation() throws Exception {
    makeCreateFieldOfStudyRequest(name)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.name", is(name)));
    Optional<FieldOfStudy> optionalFieldCreated = fieldsOfStudyService.findByName(name);
    assertTrue(optionalFieldCreated.isPresent());

    FieldOfStudy fieldCreated = optionalFieldCreated.get();
    assertEquals(name, fieldCreated.getName());
  }

  private ResultActions makeCreateFieldOfStudyRequest(String field) throws Exception {
    return mvc.perform(
      authenticated(post("/api/fields-of-study"), coordinatorToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(field)
    );
  }
}
