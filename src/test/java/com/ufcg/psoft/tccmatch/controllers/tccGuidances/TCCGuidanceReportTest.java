package com.ufcg.psoft.tccmatch.controllers.tccGuidances;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.services.tccGuidances.TCCGuidanceService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TCCGuidanceReportTest extends TCCGuidanceTests {

  @Autowired
  private TCCGuidanceService tccGuidanceService;

  private List<TCCGuidance> tccGuidances;
  private List<String> periods = List.of("2020.1", "2020.2");

  @BeforeEach
  void beforeEach() {
    student = createMockStudent();
    professor = createMockProfessor();
    tccSubject = createMockTCCSubject(student);
    coordinatorToken = loginWithDefaultCoordinator();

    tccGuidances = new ArrayList<>();

    CreateTCCGuidanceDTO tccGuidanceDTO = new CreateTCCGuidanceDTO(
      student.getId(),
      professor.getId(),
      tccSubject.getId(),
      periods.get(0)
    );
    tccGuidances.add(tccGuidanceService.createTCCGuidance(tccGuidanceDTO));

    tccGuidanceDTO =
      new CreateTCCGuidanceDTO(
        student.getId(),
        professor.getId(),
        tccSubject.getId(),
        periods.get(0)
      );
    tccGuidances.add(tccGuidanceService.createTCCGuidance(tccGuidanceDTO));

    tccGuidanceDTO =
      new CreateTCCGuidanceDTO(
        student.getId(),
        professor.getId(),
        tccSubject.getId(),
        periods.get(1)
      );
    tccGuidances.add(tccGuidanceService.createTCCGuidance(tccGuidanceDTO));

    tccGuidanceService.finishTCCGuidance(tccGuidances.get(1).getId());
    tccGuidanceService.finishTCCGuidance(tccGuidances.get(2).getId());
  }

  @Test
  void listWithoutFilters() throws Exception {
    reportTCCGuidances(coordinatorToken, Optional.empty(), Optional.empty())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.[0].id", is(tccGuidances.get(0).getId().intValue())))
      .andExpect(jsonPath("$.[1].id", is(tccGuidances.get(1).getId().intValue())))
      .andExpect(jsonPath("$.[2].id", is(tccGuidances.get(2).getId().intValue())));
  }

  @Test
  void listByPeriod() throws Exception {
    reportTCCGuidances(coordinatorToken, Optional.of(periods.get(0)), Optional.empty())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.[0].id", is(tccGuidances.get(0).getId().intValue())))
      .andExpect(jsonPath("$.[1].id", is(tccGuidances.get(1).getId().intValue())));
  }

  @Test
  void listByIsFinished() throws Exception {
    reportTCCGuidances(coordinatorToken, Optional.empty(), Optional.of(false))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.[0].id", is(tccGuidances.get(0).getId().intValue())));

      reportTCCGuidances(coordinatorToken, Optional.empty(), Optional.of(true))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.[0].id", is(tccGuidances.get(1).getId().intValue())))
      .andExpect(jsonPath("$.[1].id", is(tccGuidances.get(2).getId().intValue())));
  }

  @Test
  void listByPeriodAndIsFinished() throws Exception {
    reportTCCGuidances(coordinatorToken, Optional.of(periods.get(1)), Optional.of(true))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.[0].id", is(tccGuidances.get(2).getId().intValue())));
  }

  private ResultActions reportTCCGuidances(
    String token,
    Optional<String> period,
    Optional<Boolean> isFinished
  ) throws Exception {
    List<String> queries = new LinkedList<>();

    if (period.isPresent()) {
      queries.add(String.format("period=%s", period.get()));
    }
    if (isFinished.isPresent()) {
      queries.add(String.format("finished=%s", isFinished.get()));
    }

    return mvc.perform(
      authenticated(
        get(
          String.format(
            "/api/tcc-guidances%s%s",
            queries.size() > 0 ? "?" : "",
            String.join("&", queries)
          )
        ),
        token
      )
    );
  }
}
