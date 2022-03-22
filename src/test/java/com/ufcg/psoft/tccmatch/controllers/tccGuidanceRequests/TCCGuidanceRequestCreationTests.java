package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TCCGuidanceRequestCreationTests extends IntegrationTests {

  private Professor professor;
  private Student student;

  @BeforeEach
  void beforeEach() {
    professor = createMockProfessor();
    student = createMockStudent();
  }

  @Test
  void tccGuidanceRequestWithProfessorTCCSubject() throws Exception {
    TCCSubject tccSubject = createMockTCCSubject(professor);
    String studentToken = loginWithMockStudent();

    CreateTCCGuidanceRequestRequestDTO createTCCGuidanceRequestRequestDTO = new CreateTCCGuidanceRequestRequestDTO(
      tccSubject.getId(),
      professor.getId()
    );

    makeCreateTCCGuidanceRequestRequest(createTCCGuidanceRequestRequestDTO, studentToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.status", is("PENDING")))
      .andExpect(jsonPath("$.message", is("")))
      .andExpect(jsonPath("$.createdBy", is(student.getId().intValue())))
      .andExpect(jsonPath("$.requestedTo", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubject", is(tccSubject.getId().intValue())));
  }

  @Test
  void tccGuidanceRequestWithStudentTCCSubject() throws Exception {
    TCCSubject tccSubject = createMockTCCSubject(student);
    String studentToken = loginWithMockStudent();

    CreateTCCGuidanceRequestRequestDTO createTCCGuidanceRequestRequestDTO = new CreateTCCGuidanceRequestRequestDTO(
      tccSubject.getId(),
      professor.getId()
    );

    makeCreateTCCGuidanceRequestRequest(createTCCGuidanceRequestRequestDTO, studentToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.status", is("PENDING")))
      .andExpect(jsonPath("$.message", is("")))
      .andExpect(jsonPath("$.createdBy", is(student.getId().intValue())))
      .andExpect(jsonPath("$.requestedTo", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubject", is(tccSubject.getId().intValue())));
  }

  private ResultActions makeCreateTCCGuidanceRequestRequest(
    CreateTCCGuidanceRequestRequestDTO tccGuidanceRequestRequestDTO,
    String userToken
  ) throws Exception {
    return mvc.perform(
      authenticated(post("/api/tcc-guidance-requests"), userToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(tccGuidanceRequestRequestDTO))
    );
  }
}
