package com.ufcg.psoft.tccmatch.controllers.users.professors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.EmailAlreadyInUseException;
import com.ufcg.psoft.tccmatch.exceptions.users.EmptyUserNameException;
import com.ufcg.psoft.tccmatch.exceptions.users.InvalidEmailApiException;
import com.ufcg.psoft.tccmatch.exceptions.users.PasswordTooShortException;
import com.ufcg.psoft.tccmatch.exceptions.users.professors.LaboratoriesNotProvidedException;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class ProfessorCreationTests extends ProfessorTests {

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
      professorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.email", is(professorEmail)))
      .andExpect(jsonPath("$.name", is(professorName)))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(professorLaboratories))))
      .andExpect(jsonPath("$.guidanceQuota", is(Professor.DEFAULT_GUIDANCE_QUOTA)));

    Optional<Professor> optionalProfessorCreated = userService.findUserByEmail(professorEmail);
    assertTrue(optionalProfessorCreated.isPresent());

    Professor professorCreated = optionalProfessorCreated.get();
    assertEquals(User.Type.PROFESSOR, professorCreated.getType());
    assertEquals(professorEmail, professorCreated.getEmail());
    assertEquals(professorName, professorCreated.getName());
    assertEquals(Professor.DEFAULT_GUIDANCE_QUOTA, professorCreated.getGuidanceQuota());
  }

  @Test
  void errorOnEmailAlreadyInUse() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );

    professorService.createProfessor(createProfessorDTO);

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message", is(EmailAlreadyInUseException.message())));
  }

  @Test
  void errorOnInvalidEmail() throws Exception {
    String invalidEmail = "email.com";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      invalidEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidEmailApiException.message())));
  }

  @Test
  void errorOnPasswordTooShort() throws Exception {
    String tooShortRawPassword = "1234567";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      tooShortRawPassword,
      professorName,
      professorLaboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(PasswordTooShortException.message())));
  }

  @Test
  void errorOnEmptyName() throws Exception {
    String emptyName = "";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      professorRawPassword,
      emptyName,
      professorLaboratories
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(EmptyUserNameException.message())));
  }

  @Test
  void errorOnLaboratoriesNotProvided() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      professorRawPassword,
      professorName,
      null
    );

    makeCreateUserRequest(createProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(LaboratoriesNotProvidedException.message())));
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
