package com.ufcg.psoft.tccmatch.controllers.users.professors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ProfessorCreationTests extends IntegrationTests {

  private String email = "professor@email.com";
  private String rawPassword = "12345678";
  private String name = "Professor";
  private Set<String> laboratories = new HashSet<>();

  private String coordinatorToken;

  @Autowired
  private UserService<Professor> userService;

  @Autowired
  private ProfessorService professorService;

  @BeforeEach
  void beforeEach() {
    coordinatorToken = loginProgrammaticallyWithDefaultCoordinator();
  }

  @Test
  void validProfessorCreation() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      email,
      rawPassword,
      name,
      laboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.email", is(email)))
      .andExpect(jsonPath("$.name", is(name)))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(laboratories))))
      .andExpect(jsonPath("$.guidanceQuota", is(0)));

    Optional<Professor> optionalProfessorCreated = userService.findUserByEmail(email);
    assertTrue(optionalProfessorCreated.isPresent());

    Professor professorCreated = optionalProfessorCreated.get();
    assertEquals(User.Type.PROFESSOR, professorCreated.getType());
    assertEquals(email, professorCreated.getEmail());
    assertEquals(name, professorCreated.getName());
    assertEquals(Professor.DEFAULT_GUIDANCE_QUOTA, professorCreated.getGuidanceQuota());
  }

  @Test
  void errorOnEmailAlreadyInUse() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      email,
      rawPassword,
      name,
      laboratories
    );

    professorService.createProfessor(createProfessorDTO);

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message", is("Email already in use.")));
  }

  @Test
  void errorOnInvalidEmail() throws Exception {
    String invalidEmail = "email.com";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      invalidEmail,
      rawPassword,
      name,
      laboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Invalid email.")));
  }

  @Test
  void errorOnPasswordTooShort() throws Exception {
    String tooShortRawPassword = "1234567";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      email,
      tooShortRawPassword,
      name,
      laboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Password too short.")));
  }

  @Test
  void errorOnEmptyName() throws Exception {
    String emptyName = "";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      email,
      rawPassword,
      emptyName,
      laboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("User name is empty.")));
  }

  @Test
  void errorOnLaboratoriesNotProvided() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(email, rawPassword, name, null);

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Laboratories not provided.")));
  }

  private ResultActions makeCreateUserRequest(CreateProfessorDTO createProfessorDTO)
    throws Exception {
    return mvc.perform(
      authenticated(post("/api/users/professors"), coordinatorToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(createProfessorDTO))
    );
  }
}
