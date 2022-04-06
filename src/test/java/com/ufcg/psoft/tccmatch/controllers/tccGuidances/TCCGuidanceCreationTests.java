package com.ufcg.psoft.tccmatch.controllers.tccGuidances;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccSubjects.TCCSubjectNotFoundException;
import com.ufcg.psoft.tccmatch.exceptions.users.ProfessorNotFoundException;
import com.ufcg.psoft.tccmatch.exceptions.users.StudentNotFoundException;
import com.ufcg.psoft.tccmatch.exceptions.users.students.InvalidPeriodException;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.tccGuidances.TCCGuidanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class TCCGuidanceCreationTests extends TCCGuidanceTests {

  @Autowired
  private UserRepository<Professor> userRepository;

  @Autowired
  private TCCGuidanceService tccGuidanceService;

  @BeforeEach
  void beforeEach() {
    student = createMockStudent();

    professor = createMockProfessor();
    professor.setGuidanceQuota(1);
    userRepository.save(professor);

    tccSubject = createMockTCCSubject(student);
    coordinatorToken = loginWithDefaultCoordinator();
  }

  @Test
  void validTCCGuidanceCreation() throws Exception {
    CreateTCCGuidanceDTO createTCCGuidanceDTO = new CreateTCCGuidanceDTO(
      student.getId(),
      professor.getId(),
      tccSubject.getId(),
      period
    );

    makeCreateTCCGuidanceRequest(createTCCGuidanceDTO, coordinatorToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.studentId", is(student.getId().intValue())))
      .andExpect(jsonPath("$.professorId", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubjectId", is(tccSubject.getId().intValue())))
      .andExpect(jsonPath("$.finished", is(false)));

    TCCGuidance tccGuidanceCreated = tccGuidanceService.listAllTCCGuidances().get(0);
    assertEquals(student.getId(), tccGuidanceCreated.getStudent().getId());
    assertEquals(professor.getId(), tccGuidanceCreated.getProfessor().getId());
    assertEquals(tccSubject.getId(), tccGuidanceCreated.getTCCSubject().getId());
    assertEquals(false, tccGuidanceCreated.isFinished());
  }

  @Test
  void errorOnStudentNotFound() throws Exception {
    Long nonExistentStudentId = 1000L;

    CreateTCCGuidanceDTO createTCCGuidanceDTO = new CreateTCCGuidanceDTO(
      nonExistentStudentId,
      professor.getId(),
      tccSubject.getId(),
      period
    );

    makeCreateTCCGuidanceRequest(createTCCGuidanceDTO, coordinatorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(StudentNotFoundException.message())));
  }

  @Test
  void errorOnProfessorNotFound() throws Exception {
    Long nonExistentProfessorId = 1000L;

    CreateTCCGuidanceDTO createTCCGuidanceDTO = new CreateTCCGuidanceDTO(
      student.getId(),
      nonExistentProfessorId,
      tccSubject.getId(),
      period
    );

    makeCreateTCCGuidanceRequest(createTCCGuidanceDTO, coordinatorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(ProfessorNotFoundException.message())));
  }

  @Test
  void errorOnTCCSubjectNotFound() throws Exception {
    Long nonExistentTCCSubjectId = 1000L;

    CreateTCCGuidanceDTO createTCCGuidanceDTO = new CreateTCCGuidanceDTO(
      student.getId(),
      professor.getId(),
      nonExistentTCCSubjectId,
      period
    );

    makeCreateTCCGuidanceRequest(createTCCGuidanceDTO, coordinatorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(TCCSubjectNotFoundException.message())));
  }

  @Test
  void errorOnInvalidPeriod() throws Exception {
    String invalidPeriod = "20201";

    CreateTCCGuidanceDTO createTCCGuidanceDTO = new CreateTCCGuidanceDTO(
      student.getId(),
      professor.getId(),
      tccSubject.getId(),
      invalidPeriod
    );

    makeCreateTCCGuidanceRequest(createTCCGuidanceDTO, coordinatorToken)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidPeriodException.message())));
  }

  private ResultActions makeCreateTCCGuidanceRequest(
    CreateTCCGuidanceDTO createTCCGuidanceDTO,
    String coordinatorToken
  ) throws Exception {
    return mvc.perform(
      authenticated(post("/api/tcc-guidances"), coordinatorToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(createTCCGuidanceDTO))
    );
  }
}
