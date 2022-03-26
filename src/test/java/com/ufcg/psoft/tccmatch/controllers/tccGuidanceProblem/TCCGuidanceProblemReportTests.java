package com.ufcg.psoft.tccmatch.controllers.tccGuidanceProblem;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.CreateTCCGuidanceProblemDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.services.tccGuidanceProblem.TCCGuidanceProblemService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TCCGuidanceProblemReportTests extends TCCGuidanceProblemTests {

  @Autowired
  private TCCGuidanceProblemService tccGuidanceProblemService;

  private List<TCCGuidanceProblem> tccGuidanceProblems;

  @BeforeEach
  void beforeEach() {
    student = createMockStudent();
    professor = createMockProfessor();
    tccSubject = createMockTCCSubject(student);
    tccGuidance =
      createMockTCCGuidance(student.getId(), professor.getId(), tccSubject.getId(), period);
    coordinatorToken = loginWithDefaultCoordinator();

    tccGuidanceProblems = new ArrayList<>();

    CreateTCCGuidanceProblemDTO tccGuidanceProblemDTO = new CreateTCCGuidanceProblemDTO(
      category,
      description,
      tccGuidance.getId()
    );
    tccGuidanceProblems.add(
      tccGuidanceProblemService.createTCCGuidanceProblem(tccGuidanceProblemDTO, student)
    );

    tccGuidanceProblemDTO =
      new CreateTCCGuidanceProblemDTO(category, description, tccGuidance.getId());
    tccGuidanceProblems.add(
      tccGuidanceProblemService.createTCCGuidanceProblem(tccGuidanceProblemDTO, professor)
    );
  }

  @Test
  void listTCCGuidanceProblemsGroupedByStudentsAndProfessors() throws Exception {
    makeReportTCCGuidanceProblemsRequest(coordinatorToken)
      .andExpect(status().isOk())
      .andExpect(
        jsonPath("$.studentProblems.[0].id", is(tccGuidanceProblems.get(0).getId().intValue()))
      )
      .andExpect(
        jsonPath("$.professorProblems.[0].id", is(tccGuidanceProblems.get(1).getId().intValue()))
      );
  }

  private ResultActions makeReportTCCGuidanceProblemsRequest(String token) throws Exception {
    return mvc.perform(authenticated(get("/api/tcc-guidance-problems/reports"), token));
  }
}
