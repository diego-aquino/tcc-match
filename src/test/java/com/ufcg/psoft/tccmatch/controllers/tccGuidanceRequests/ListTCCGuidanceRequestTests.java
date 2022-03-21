package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
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

  @Autowired
  private TCCSubjectService tccSubjectService;

  @Autowired
  private TCCGuidanceRequestService tccGuidanceRequestService;

  @BeforeEach
  void beforeEach() {
    createMockProfessor();
    createMockStudent();
    createMockTCCSubject(mockProfessor);
  }

  @Test
  void ListTCCGuidanceRequest() throws Exception {
    String professorToken = loginProgrammaticallyWithMockProfessor();

    CreateTCCGuidanceRequestRequestDTO createTCCGuidanceRequestRequestDTO = new CreateTCCGuidanceRequestRequestDTO(
      mockTCCSubject.getId(),
      mockProfessor.getId()
    );
    tccGuidanceRequestService.createTCCGuidanceRequest(
      createTCCGuidanceRequestRequestDTO,
      mockStudent
    );

    makeListTCCGuidanceRequestRequest(professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void ListTCCGuidanceNoRequests() throws Exception {
    String professorToken = loginProgrammaticallyWithMockProfessor();

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
