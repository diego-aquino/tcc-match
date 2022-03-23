package com.ufcg.psoft.tccmatch.controllers.users.professors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.ProfessorNotFoundException;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

class ProfessorRemovalTests extends ProfessorTests {

  @Autowired
  private ProfessorService professorService;

  @Autowired
  private UserService<Professor> userService;

  private Professor professor;

  private String coordinatorToken;

  @BeforeEach
  void beforeEach() {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );

    professor = professorService.createProfessor(createProfessorDTO);

    coordinatorToken = loginWithDefaultCoordinator();
  }

  @Test
  void validRemoval() throws Exception {
    makeRemoveProfessorRequest(professor.getId(), coordinatorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.email", is(professor.getEmail())))
      .andExpect(jsonPath("$.name", is(professor.getName())))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(professor.getLaboratories()))))
      .andExpect(jsonPath("$.guidanceQuota", is(professor.getGuidanceQuota())));

    Optional<Professor> optionalProfessorRemoved = userService.findUserById(professor.getId());
    assertTrue(optionalProfessorRemoved.isEmpty());
  }

  @Test
  void errorOnProfessorNotFound() throws Exception {
    Long nonExistentProfessorId = 1000L;

    makeRemoveProfessorRequest(nonExistentProfessorId, coordinatorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(ProfessorNotFoundException.message())));
  }

  private ResultActions makeRemoveProfessorRequest(Long professorId, String token)
    throws Exception {
    String endpoint = String.format("/api/users/professors/%d", professorId);
    return mvc.perform(authenticated(delete(endpoint), token));
  }
}
