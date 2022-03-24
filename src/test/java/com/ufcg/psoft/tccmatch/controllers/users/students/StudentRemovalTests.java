package com.ufcg.psoft.tccmatch.controllers.users.students;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.StudentNotFoundException;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

class StudentRemovalTests extends StudentTests {

  @Autowired
  private StudentService studentService;

  @Autowired
  private UserService<Student> userService;

  private Student student;

  private String coordinatorToken;

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

    coordinatorToken = loginWithDefaultCoordinator();
  }

  @Test
  void validRemoval() throws Exception {
    makeRemoveStudentRequest(student.getId(), coordinatorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(student.getId().intValue())))
      .andExpect(jsonPath("$.email", is(student.getEmail())))
      .andExpect(jsonPath("$.name", is(student.getName())))
      .andExpect(jsonPath("$.registryNumber", is(student.getRegistryNumber())))
      .andExpect(jsonPath("$.completionPeriod", is(student.getCompletionPeriod())));

    Optional<Student> optionalStudentRemoved = userService.findUserById(student.getId());
    assertTrue(optionalStudentRemoved.isEmpty());
  }

  @Test
  void errorOnStudentNotFound() throws Exception {
    Long nonExistentStudentId = 1000L;

    makeRemoveStudentRequest(nonExistentStudentId, coordinatorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(StudentNotFoundException.message())));
  }

  private ResultActions makeRemoveStudentRequest(Long studentId, String token) throws Exception {
    String endpoint = String.format("/api/users/students/%d", studentId);
    return mvc.perform(authenticated(delete(endpoint), token));
  }
}
