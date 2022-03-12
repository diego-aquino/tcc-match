package com.ufcg.psoft.tccmatch.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentsControllerTests extends IntegrationTests {

  private String email = "student@email.com";
  private String rawPassword = "12345678";
  private String name = "Student";
  private String registryNumber = "111000111";
  private String completionPeriod = "2024.1";

  private String coordinatorToken;

  @Autowired
  private UserService userService;

  @Autowired
  private StudentService studentService;

  @BeforeEach
  void beforeEach() {
    coordinatorToken = loginProgrammaticallyWithDefaultCoordinator();
  }

  @Test
  void validStudentCreation() throws Exception {
    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      email,
      rawPassword,
      name,
      registryNumber,
      completionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.email", is(email)))
      .andExpect(jsonPath("$.name", is(name)))
      .andExpect(jsonPath("$.registryNumber", is(registryNumber)))
      .andExpect(jsonPath("$.completionPeriod", is(completionPeriod)));

    Optional<User> optionalStudentCreated = userService.findUserByEmail(email);
    assertTrue(optionalStudentCreated.isPresent());

    User studentCreated = optionalStudentCreated.get();
    assertEquals(email, studentCreated.getEmail());
    assertEquals(User.Type.STUDENT, studentCreated.getType());
  }

  @Test
  void errorOnEmailAlreadyInUse() throws Exception {
    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      email,
      rawPassword,
      name,
      registryNumber,
      completionPeriod
    );

    studentService.createStudent(createStudentDTO);

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message", is("Email already in use.")));
  }

  @Test
  void errorOnInvalidEmail() throws Exception {
    String invalidEmail = "email.com";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      invalidEmail,
      rawPassword,
      name,
      registryNumber,
      completionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Invalid email.")));
  }

  @Test
  void errorOnPasswordTooShort() throws Exception {
    String tooShortRawPassword = "1234567";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      email,
      tooShortRawPassword,
      name,
      registryNumber,
      completionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Password too short.")));
  }

  @Test
  void errorOnEmptyName() throws Exception {
    String emptyName = "";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      email,
      rawPassword,
      emptyName,
      registryNumber,
      completionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("User name is empty.")));
  }

  @Test
  void errorOnInvalidRegistryNumber() throws Exception {
    String invalidRegistryNumber = "11100011";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      email,
      rawPassword,
      name,
      invalidRegistryNumber,
      completionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Invalid registry number.")));
  }

  @Test
  void errorOnInvalidCompletionPeriod() throws Exception {
    String invalidCompletionPeriod = "20201";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      email,
      rawPassword,
      name,
      registryNumber,
      invalidCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is("Invalid completion period.")));
  }

  private ResultActions makeCreateStudentRequest(CreateStudentDTO createStudentDTO)
    throws Exception {
    return mvc.perform(
      authenticated(post("/api/users/students"), coordinatorToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(createStudentDTO))
    );
  }
}
