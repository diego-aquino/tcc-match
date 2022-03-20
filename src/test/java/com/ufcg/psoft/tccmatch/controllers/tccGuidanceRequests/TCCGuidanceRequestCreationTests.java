package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
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
class TCCGuidanceRequestCreationTests extends IntegrationTests {

  private String userToken;

  @Autowired
  private TCCSubjectService tccSubjectService;

  @Autowired
  private TCCGuidanceRequestService tccGuidanceRequestService;

  @BeforeEach
  void beforeEach() {
    createMockProfessor();
    createMockStudent();
  }

  @Test
  void TCCGuidanceRequestWithProfessorTCCSubject() throws Exception {
    createMockTCCSubject(mockProfessor);
    userToken = loginProgrammaticallyWithMockStudent();

    CreateTCCGuidanceRequestRequestDTO createTCCGuidanceRequestRequestDTO = new CreateTCCGuidanceRequestRequestDTO(
      mockTCCSubject.getId(),
      mockProfessor.getId()
    );

    makeCreateTCCGuidanceRequestRequest(createTCCGuidanceRequestRequestDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.status", is("PENDING")))
      .andExpect(jsonPath("$.message", is("")))
      .andExpect(jsonPath("$.createdBy", is(mockStudent.getId().intValue())))
      .andExpect(jsonPath("$.requestedTo", is(mockProfessor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubject", is(mockTCCSubject.getId().intValue())));
  }

  @Test
  void TCCGuidanceRequestWithStudentTCCSubject() throws Exception {
    createMockTCCSubject(mockStudent);
    userToken = loginProgrammaticallyWithMockStudent();

    CreateTCCGuidanceRequestRequestDTO createTCCGuidanceRequestRequestDTO = new CreateTCCGuidanceRequestRequestDTO(
      mockTCCSubject.getId(),
      mockProfessor.getId()
    );

    makeCreateTCCGuidanceRequestRequest(createTCCGuidanceRequestRequestDTO)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.status", is("PENDING")))
      .andExpect(jsonPath("$.message", is("")))
      .andExpect(jsonPath("$.createdBy", is(mockStudent.getId().intValue())))
      .andExpect(jsonPath("$.requestedTo", is(mockProfessor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubject", is(mockTCCSubject.getId().intValue())));
  }

  private ResultActions makeCreateTCCGuidanceRequestRequest(
    CreateTCCGuidanceRequestRequestDTO tccGuidanceRequestRequestDTO
  ) throws Exception {
    return mvc.perform(
      authenticated(post("/api/tcc-guidance-requests"), userToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(tccGuidanceRequestRequestDTO))
    );
  }
}
