package com.ufcg.psoft.tccmatch.controllers.tccSubjects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TCCSubjectCreationTests extends IntegrationTests {

  @Autowired
  private TCCSubjectService tccSubjectService;

  @Test
  void TCCSubjectCreationByStudent() throws Exception {
    Student student = createMockStudent();
    String studentToken = loginWithMockStudent();

    CreateTCCSubjectRequestDTO createTCCSubjectRequestDTO = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle,
      tccSubjectDescription,
      tccSubjectStatus,
      tccSubjectFieldsOfStudy
    );

    makeCreateTCCSubjectRequest(createTCCSubjectRequestDTO, studentToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.title", is(tccSubjectTitle)))
      .andExpect(jsonPath("$.description", is(tccSubjectDescription)))
      .andExpect(jsonPath("$.status", is(tccSubjectStatus)))
      .andExpect(jsonPath("$.fieldsOfStudy", is(List.copyOf(tccSubjectFieldsOfStudy))))
      .andExpect(jsonPath("$.createdBy", is(student.getId().intValue())));

    Optional<TCCSubject> optionalTCCSubjectCreated = tccSubjectService.findTCCSubjectByTitle(
      tccSubjectTitle
    );
    assertTrue(optionalTCCSubjectCreated.isPresent());

    TCCSubject tccSubjectCreated = optionalTCCSubjectCreated.get();

    assertEquals(tccSubjectTitle, tccSubjectCreated.getTitle());
    assertEquals(tccSubjectDescription, tccSubjectCreated.getDescription());
    assertEquals(tccSubjectStatus, tccSubjectCreated.getStatus());
    assertEquals(student.getId(), tccSubjectCreated.getCreatedBy().getId().intValue());
    assertEquals(User.Type.STUDENT, tccSubjectCreated.getCreatedBy().getType());
  }

  @Test
  void TCCSubjectCreationByProfessor() throws Exception {
    Professor professor = createMockProfessor();
    String professorToken = loginWithMockProfessor();

    CreateTCCSubjectRequestDTO createTCCSubjectRequestDTO = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle,
      tccSubjectDescription,
      tccSubjectStatus,
      tccSubjectFieldsOfStudy
    );

    makeCreateTCCSubjectRequest(createTCCSubjectRequestDTO, professorToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.title", is(tccSubjectTitle)))
      .andExpect(jsonPath("$.description", is(tccSubjectDescription)))
      .andExpect(jsonPath("$.status", is(tccSubjectStatus)))
      .andExpect(jsonPath("$.fieldsOfStudy", is(List.copyOf(tccSubjectFieldsOfStudy))))
      .andExpect(jsonPath("$.createdBy", is(professor.getId().intValue())));

    Optional<TCCSubject> optionalTCCSubjectCreated = tccSubjectService.findTCCSubjectByTitle(
      tccSubjectTitle
    );
    assertTrue(optionalTCCSubjectCreated.isPresent());

    TCCSubject tccSubjectCreated = optionalTCCSubjectCreated.get();

    assertEquals(tccSubjectTitle, tccSubjectCreated.getTitle());
    assertEquals(tccSubjectDescription, tccSubjectCreated.getDescription());
    assertEquals(tccSubjectStatus, tccSubjectCreated.getStatus());
    assertEquals(professor.getId(), tccSubjectCreated.getCreatedBy().getId());
    assertEquals(User.Type.PROFESSOR, tccSubjectCreated.getCreatedBy().getType());
  }

  private ResultActions makeCreateTCCSubjectRequest(
    CreateTCCSubjectRequestDTO tccSubjectRequestDTO,
    String userToken
  ) throws Exception {
    return mvc.perform(
      authenticated(post("/api/tcc-subjects"), userToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(tccSubjectRequestDTO))
    );
  }
}
