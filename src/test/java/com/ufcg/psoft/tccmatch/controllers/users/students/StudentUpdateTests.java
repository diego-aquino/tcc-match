package com.ufcg.psoft.tccmatch.controllers.users.students;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.dto.users.UpdateStudentDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.EmailAlreadyInUseException;
import com.ufcg.psoft.tccmatch.exceptions.users.EmptyUserNameException;
import com.ufcg.psoft.tccmatch.exceptions.users.ForbiddenUserUpdateException;
import com.ufcg.psoft.tccmatch.exceptions.users.InvalidEmailApiException;
import com.ufcg.psoft.tccmatch.exceptions.users.students.InvalidCompletionPeriodException;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class StudentUpdateTests extends StudentTests {

  @Autowired
  private StudentService studentService;

  private Student student;

  private String studentToken;

  @BeforeEach
  void beforeEach() {
    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    student = studentService.createStudent(createStudentDTO);
    studentToken = loginProgrammatically(studentEmail, studentRawPassword);
  }

  @Test
  void validNameUpdate() throws Exception {
    String newName = "New Student";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.empty(),
      Optional.of(newName),
      Optional.empty()
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(student.getId().intValue())))
      .andExpect(jsonPath("$.email", is(student.getEmail())))
      .andExpect(jsonPath("$.name", is(newName)))
      .andExpect(jsonPath("$.registryNumber", is(student.getRegistryNumber())))
      .andExpect(jsonPath("$.completionPeriod", is(student.getCompletionPeriod())));
  }

  @Test
  void validEmailUpdate() throws Exception {
    String newEmail = "newstudent@email.com";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.of(newEmail),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(student.getId().intValue())))
      .andExpect(jsonPath("$.email", is(newEmail)))
      .andExpect(jsonPath("$.name", is(student.getName())))
      .andExpect(jsonPath("$.registryNumber", is(student.getRegistryNumber())))
      .andExpect(jsonPath("$.completionPeriod", is(student.getCompletionPeriod())));
  }

  @Test
  void validCompletionPeriodUpdate() throws Exception {
    String newCompletionPeriod = "2020.1";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.empty(),
      Optional.empty(),
      Optional.of(newCompletionPeriod)
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(student.getId().intValue())))
      .andExpect(jsonPath("$.email", is(student.getEmail())))
      .andExpect(jsonPath("$.name", is(student.getName())))
      .andExpect(jsonPath("$.registryNumber", is(student.getRegistryNumber())))
      .andExpect(jsonPath("$.completionPeriod", is(newCompletionPeriod)));
  }

  @Test
  void errorOnEmailAlreadyInUse() throws Exception {
    String existingStudentEmail = "existingstudent@email.com";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      existingStudentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );
    studentService.createStudent(createStudentDTO);

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.of(existingStudentEmail),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message", is(EmailAlreadyInUseException.message())));
  }

  @Test
  void errorOnInvalidEmail() throws Exception {
    String invalidEmail = "email.com";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.of(invalidEmail),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidEmailApiException.message())));
  }

  @Test
  void errorOnEmptyName() throws Exception {
    String emptyName = "";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.empty(),
      Optional.of(emptyName),
      Optional.empty()
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(EmptyUserNameException.message())));
  }

  @Test
  void errorOnInvalidCompletionPeriod() throws Exception {
    String invalidCompletionPeriod = "20201";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.empty(),
      Optional.empty(),
      Optional.of(invalidCompletionPeriod)
    );

    makeUpdateStudentRequest(student.getId(), studentToken, updateStudentDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidCompletionPeriodException.message())));
  }

  @Test
  void errorOnMissingPermissionToUpdate() throws Exception {
    String anotherStudentEmail = "anotherstudent@email.com";

    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      anotherStudentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );

    studentService.createStudent(createStudentDTO);
    String anotherStudentToken = loginProgrammatically(anotherStudentEmail, studentRawPassword);

    String newName = "New Student";

    UpdateStudentDTO updateStudentDTO = new UpdateStudentDTO(
      Optional.empty(),
      Optional.of(newName),
      Optional.empty()
    );

    makeUpdateStudentRequest(student.getId(), anotherStudentToken, updateStudentDTO)
      .andExpect(status().isForbidden())
      .andExpect(jsonPath("$.message", is(ForbiddenUserUpdateException.message())));
  }

  private ResultActions makeUpdateStudentRequest(
    Long studentId,
    String token,
    UpdateStudentDTO updateStudentDTO
  ) throws Exception {
    String endpoint = String.format("/api/users/students/%d", studentId);

    return mvc.perform(
      authenticated(patch(endpoint), token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(updateStudentDTO))
    );
  }
}
