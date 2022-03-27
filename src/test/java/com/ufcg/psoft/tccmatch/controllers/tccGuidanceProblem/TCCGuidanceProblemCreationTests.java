package com.ufcg.psoft.tccmatch.controllers.tccGuidanceProblem;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.CreateTCCGuidanceProblemDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceProblem.InvalidCategoryException;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidances.TCCGuidanceNotFoundException;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class TCCGuidanceProblemCreationTests extends TCCGuidanceProblemTests {

  @Autowired
  private UserRepository<Professor> userRepository;

  @BeforeEach
  void beforeEach() {
    student = createMockStudent();

    professor = createMockProfessor();
    professor.setGuidanceQuota(1);
    userRepository.save(professor);

    tccSubject = createMockTCCSubject(student);
    tccGuidance =
      createMockTCCGuidance(student.getId(), professor.getId(), tccSubject.getId(), period);
    studentToken = loginWithMockStudent();
  }

  @Test
  void validTCCGuidanceProblemCreation() throws Exception {
    CreateTCCGuidanceProblemDTO createTCCGuidanceProblemDTO = new CreateTCCGuidanceProblemDTO(
      category,
      description,
      tccGuidance.getId()
    );

    makeCreateTCCGuidanceProblemRequest(createTCCGuidanceProblemDTO, studentToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.category", is(createTCCGuidanceProblemDTO.getCategory())))
      .andExpect(jsonPath("$.description", is(createTCCGuidanceProblemDTO.getDescription())))
      .andExpect(jsonPath("$.tccGuidance.id", is(tccGuidance.getId().intValue())));
  }

  @Test
  void errorOnTCCGuidanceNotFound() throws Exception {
    Long tccGuidanceIdNotFound = 1000L;

    CreateTCCGuidanceProblemDTO createTCCGuidanceProblemDTO = new CreateTCCGuidanceProblemDTO(
      category,
      description,
      tccGuidanceIdNotFound
    );

    makeCreateTCCGuidanceProblemRequest(createTCCGuidanceProblemDTO, studentToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(TCCGuidanceNotFoundException.message())));
  }

  @Test
  void errorOnCategoryIsNotProvided() throws Exception {
    String categoryNotProvided = null;

    CreateTCCGuidanceProblemDTO createTCCGuidanceProblemDTO = new CreateTCCGuidanceProblemDTO(
      categoryNotProvided,
      description,
      tccGuidance.getId()
    );

    makeCreateTCCGuidanceProblemRequest(createTCCGuidanceProblemDTO, studentToken)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidCategoryException.message())));
  }

  private ResultActions makeCreateTCCGuidanceProblemRequest(
    CreateTCCGuidanceProblemDTO createTCCGuidanceProblemDTO,
    String userToken
  ) throws Exception {
    return mvc.perform(
      authenticated(post("/api/tcc-guidance-problems"), userToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(createTCCGuidanceProblemDTO))
    );
  }
}
