package com.ufcg.psoft.tccmatch.controllers.tccSubjects;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ListTCCSubjectTests extends IntegrationTests {

  private String TCCSubjectTitle = "IA: Salvadora da terra, ou fim dos tempos?";
  private String TCCSubjectDescription =
    "Um estudo sobre as diversas implicações do acanço de IA na tecnologia.";
  private String TCCSubjectStatus = "Nas etapas finais...";
  private Set<FieldOfStudy> TCCSubjectFieldsOfStudy = new HashSet<FieldOfStudy>();

  private String userToken;

  @Autowired
  private TCCSubjectService tccSubjectService;

  @BeforeEach
  void beforeEach() {
    createMockProfessor();
    createMockStudent();

    CreateTCCSubjectRequestDTO mockTCCSubject1 = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle,
      TCCSubjectDescription,
      TCCSubjectStatus,
      TCCSubjectFieldsOfStudy
    );

    CreateTCCSubjectRequestDTO mockTCCSubject2 = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle + " 2",
      TCCSubjectDescription + " 2",
      TCCSubjectStatus + " 2",
      TCCSubjectFieldsOfStudy
    );

    CreateTCCSubjectRequestDTO mockTCCSubject3 = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle + " 3",
      TCCSubjectDescription + " 3",
      TCCSubjectStatus + " 3",
      TCCSubjectFieldsOfStudy
    );
    CreateTCCSubjectRequestDTO mockTCCSubject4 = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle + " 4",
      TCCSubjectDescription + " 4",
      TCCSubjectStatus + " 4",
      TCCSubjectFieldsOfStudy
    );

    CreateTCCSubjectRequestDTO mockTCCSubject5 = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle + " 5",
      TCCSubjectDescription + " 5",
      TCCSubjectStatus + " 5",
      TCCSubjectFieldsOfStudy
    );
    tccSubjectService.createTCCSubject(mockTCCSubject1, mockProfessor);
    tccSubjectService.createTCCSubject(mockTCCSubject2, mockProfessor);
    tccSubjectService.createTCCSubject(mockTCCSubject3, mockProfessor);
    tccSubjectService.createTCCSubject(mockTCCSubject4, mockStudent);
    tccSubjectService.createTCCSubject(mockTCCSubject5, mockStudent);
  }

  @Test
  void ListTCCSubjectsCreatedByProfessors() throws Exception {
    userToken = loginProgrammaticallyWithMockStudent();

    makeListTCCJubjectsRequest()
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void ListTCCSubjectsCreatedByStudents() throws Exception {
    userToken = loginProgrammaticallyWithMockProfessor();

    makeListTCCJubjectsRequest()
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void ListTCCSubjectsCreatedByProfessor() throws Exception {
    userToken = loginProgrammaticallyWithMockProfessor(); //Remember to change this

    makeListTCCJubjectsRequestByProfessor(mockProfessor.getId())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void UnauthorizedListTCCSubjectsCreatedByProfessor() throws Exception { //Unauthorized Professor trying to reach other professors TCCSubjects
    userToken = loginProgrammaticallyWithMockProfessor();

    makeListTCCJubjectsRequestByProfessor(mockProfessor.getId())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(3)));
  }

  private ResultActions makeListTCCJubjectsRequest() throws Exception {
    return mvc.perform(
      authenticated(get("/api/tcc-subjects"), userToken).contentType(MediaType.APPLICATION_JSON)
    );
  }

  private ResultActions makeListTCCJubjectsRequestByProfessor(long professorId) throws Exception {
    return mvc.perform(
      authenticated(get("/api/tcc-subjects?createdBy=" + professorId), userToken)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }
}
