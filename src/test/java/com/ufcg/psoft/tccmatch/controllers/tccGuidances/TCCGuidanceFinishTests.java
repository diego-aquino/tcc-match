package com.ufcg.psoft.tccmatch.controllers.tccGuidances;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.exceptions.tccGuidances.TCCGuidanceNotFoundException;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.services.tccGuidances.TCCGuidanceService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

class TCCGuidanceFinishTests extends TCCGuidanceTests {

  @Autowired
  private TCCGuidanceService tccGuidanceService;

  private TCCGuidance tccGuidance;

  @BeforeEach
  void beforeEach() {
    student = createMockStudent();
    professor = createMockProfessor();
    tccSubject = createMockTCCSubject(student);
    coordinatorToken = loginWithDefaultCoordinator();

    tccGuidance =
      createMockTCCGuidance(student.getId(), professor.getId(), tccSubject.getId(), period);
  }

  @Test
  void validFinish() throws Exception {
    makeFinishTCCGuidanceRequest(tccGuidance.getId(), coordinatorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(tccGuidance.getId().intValue())))
      .andExpect(jsonPath("$.studentId", is(student.getId().intValue())))
      .andExpect(jsonPath("$.professorId", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubjectId", is(tccSubject.getId().intValue())))
      .andExpect(jsonPath("$.finished", is(true)));

    Optional<TCCGuidance> optionalTCCGuidanceFinished = tccGuidanceService.findTCCGuidanceById(
      tccGuidance.getId()
    );
    assertTrue(optionalTCCGuidanceFinished.isPresent());

    TCCGuidance tccGuidance = optionalTCCGuidanceFinished.get();
    assertEquals(student.getId(), tccGuidance.getStudent().getId());
    assertEquals(professor.getId(), tccGuidance.getProfessor().getId());
    assertEquals(tccSubject.getId(), tccGuidance.getTCCSubject().getId());
    assertEquals(true, tccGuidance.isFinished());
  }

  @Test
  void errorOnTCCGuidanceNotFound() throws Exception {
    Long nonExistentTCCGuidanceId = 1000L;

    makeFinishTCCGuidanceRequest(nonExistentTCCGuidanceId, coordinatorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(TCCGuidanceNotFoundException.message())));
  }

  private ResultActions makeFinishTCCGuidanceRequest(Long tccGuidanceId, String coordinatorToken)
    throws Exception {
    return mvc.perform(
      authenticated(
        post(String.format("/api/tcc-guidances/finish/%d", tccGuidanceId)),
        coordinatorToken
      )
    );
  }
}
