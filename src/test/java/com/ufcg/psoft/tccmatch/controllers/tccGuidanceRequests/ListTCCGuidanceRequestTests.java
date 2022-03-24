package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ListTCCGuidanceRequestTests extends IntegrationTests {

  private Professor professor;
  private Student student;
  private TCCSubject tccSubject;

  @Autowired
  private TCCGuidanceRequestService tccGuidanceRequestService;

  @BeforeEach
  void beforeEach() {
    professor = createMockProfessor();
    student = createMockStudent();
    tccSubject = createMockTCCSubject(professor);
  }

  @Test
  void ListTCCGuidanceRequest() throws Exception {
    String professorToken = loginWithMockProfessor();

    CreateTCCGuidanceRequestRequestDTO createTCCGuidanceRequestRequestDTO = new CreateTCCGuidanceRequestRequestDTO(
      tccSubject.getId(),
      professor.getId()
    );
    TCCGuidanceRequest tccGuidanceRequest = tccGuidanceRequestService.createTCCGuidanceRequest(
      createTCCGuidanceRequestRequestDTO,
      student
    );

    makeListTCCGuidanceRequestRequest(professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$.[0].id", is(tccGuidanceRequest.getId().intValue())))
      .andExpect(jsonPath("$.[0].status", is(tccGuidanceRequest.getStatus().toString())))
      .andExpect(jsonPath("$.[0].message", is(tccGuidanceRequest.getMessage())))
      .andExpect(
        jsonPath("$.[0].createdBy", is(tccGuidanceRequest.getCreatedBy().getId().intValue()))
      )
      .andExpect(
        jsonPath("$.[0].requestedTo", is(tccGuidanceRequest.getRequestedTo().getId().intValue()))
      )
      .andExpect(
        jsonPath("$.[0].tccSubject", is(tccGuidanceRequest.getTccSubject().getId().intValue()))
      );
  }

  @Test
  void ListTCCGuidanceNoRequests() throws Exception {
    String professorToken = loginWithMockProfessor();

    makeListTCCGuidanceRequestRequest(professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(0)));
  }

  private ResultActions makeListTCCGuidanceRequestRequest(String userToken) throws Exception {
    return mvc.perform(
      authenticated(get("/api/tcc-guidance-requests"), userToken)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }
}
