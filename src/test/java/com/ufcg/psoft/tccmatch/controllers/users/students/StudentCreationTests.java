package com.ufcg.psoft.tccmatch.controllers.users.students;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.EmailAlreadyInUseException;
import com.ufcg.psoft.tccmatch.exceptions.users.EmptyUserNameException;
import com.ufcg.psoft.tccmatch.exceptions.users.InvalidEmailApiException;
import com.ufcg.psoft.tccmatch.exceptions.users.PasswordTooShortException;
import com.ufcg.psoft.tccmatch.exceptions.users.students.InvalidPeriodException;
import com.ufcg.psoft.tccmatch.exceptions.users.students.InvalidRegistryNumberException;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class StudentCreationTests extends StudentTests {

  @Autowired
  private UserService<Student> userService;

  @Autowired
  private StudentService studentService;

  private String coordinatorToken;

  @BeforeEach
  void beforeEach() {
    coordinatorToken = loginWithDefaultCoordinator();
  }

  @Test
  void validStudentCreation() throws Exception {
    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.email", is(studentEmail)))
      .andExpect(jsonPath("$.name", is(studentName)))
      .andExpect(jsonPath("$.registryNumber", is(studentRegistryNumber)))
      .andExpect(jsonPath("$.completionPeriod", is(studentCompletionPeriod)));

    Optional<Student> optionalStudentCreated = userService.findUserByEmail(studentEmail);
    assertTrue(optionalStudentCreated.isPresent());

    Student studentCreated = optionalStudentCreated.get();
    assertEquals(User.Type.STUDENT, studentCreated.getType());
    assertEquals(studentEmail, studentCreated.getEmail());
    assertEquals(studentName, studentCreated.getName());
    assertEquals(studentRegistryNumber, studentCreated.getRegistryNumber());
    assertEquals(studentCompletionPeriod, studentCreated.getCompletionPeriod());
  }

  @Test
  void errorOnEmailAlreadyInUse() throws Exception {
    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    studentService.createStudent(createStudentDTO);

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message", is(EmailAlreadyInUseException.message())));
  }

  @Test
  void errorOnInvalidEmail() throws Exception {
    String invalidEmail = "email.com";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      invalidEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidEmailApiException.message())));
  }

  @Test
  void errorOnPasswordTooShort() throws Exception {
    String tooShortRawPassword = "1234567";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      tooShortRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(PasswordTooShortException.message())));
  }

  @Test
  void errorOnEmptyName() throws Exception {
    String emptyName = "";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      emptyName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(EmptyUserNameException.message())));
  }

  @Test
  void errorOnInvalidRegistryNumber() throws Exception {
    String invalidRegistryNumber = "11100011";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      studentName,
      invalidRegistryNumber,
      studentCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidRegistryNumberException.message())));
  }

  @Test
  void errorOnInvalidCompletionPeriod() throws Exception {
    String invalidCompletionPeriod = "20201";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      invalidCompletionPeriod
    );

    makeCreateStudentRequest(createStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidPeriodException.message())));
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
