package com.ufcg.psoft.tccmatch.controllers.tccSubjects;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
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

  private Professor professor;
  private Student student;

  private String tccSubjectTitle = "IA: Salvadora da terra, ou fim dos tempos?";
  private String tccSubjectDescription =
    "Um estudo sobre as diversas implicações do acanço de IA na tecnologia.";
  private String tccSubjectStatus = "Nas etapas finais...";
  private Set<FieldOfStudy> tccSubjectFieldsOfStudy = new HashSet<>();

  @Autowired
  private TCCSubjectService tccSubjectService;

  @BeforeEach
  void beforeEach() {
    professor = createMockProfessor();
    student = createMockStudent();

    CreateTCCSubjectRequestDTO mockTCCSubject1 = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle,
      tccSubjectDescription,
      tccSubjectStatus,
      tccSubjectFieldsOfStudy
    );

    CreateTCCSubjectRequestDTO mockTCCSubject2 = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle + " 2",
      tccSubjectDescription + " 2",
      tccSubjectStatus + " 2",
      tccSubjectFieldsOfStudy
    );

    CreateTCCSubjectRequestDTO mockTCCSubject3 = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle + " 3",
      tccSubjectDescription + " 3",
      tccSubjectStatus + " 3",
      tccSubjectFieldsOfStudy
    );
    CreateTCCSubjectRequestDTO mockTCCSubject4 = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle + " 4",
      tccSubjectDescription + " 4",
      tccSubjectStatus + " 4",
      tccSubjectFieldsOfStudy
    );

    CreateTCCSubjectRequestDTO mockTCCSubject5 = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle + " 5",
      tccSubjectDescription + " 5",
      tccSubjectStatus + " 5",
      tccSubjectFieldsOfStudy
    );
    tccSubjectService.createTCCSubject(mockTCCSubject1, professor);
    tccSubjectService.createTCCSubject(mockTCCSubject2, professor);
    tccSubjectService.createTCCSubject(mockTCCSubject3, professor);
    tccSubjectService.createTCCSubject(mockTCCSubject4, student);
    tccSubjectService.createTCCSubject(mockTCCSubject5, student);
  }

  @Test
  void listTCCSubjectsCreatedByProfessors() throws Exception {
    String studentToken = loginWithMockStudent();

    makeListTCCSubjectsRequest(studentToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void listTCCSubjectsCreatedByStudents() throws Exception {
    String professorToken = loginWithMockProfessor();

    makeListTCCSubjectsRequest(professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void listTCCSubjectsCreatedByProfessor() throws Exception {
    String professorToken = loginWithMockProfessor();

    makeListTCCSubjectsRequestByProfessor(professor.getId(), professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void unauthorizedListTCCSubjectsCreatedByProfessor() throws Exception { //Unauthorized Professor trying to reach other professors TCCSubjects
    String professorToken = loginWithMockProfessor();

    makeListTCCSubjectsRequestByProfessor(professor.getId(), professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(any(List.class))))
      .andExpect(jsonPath("$", hasSize(3)));
  }

  private ResultActions makeListTCCSubjectsRequest(String userToken) throws Exception {
    return mvc.perform(
      authenticated(get("/api/tcc-subjects"), userToken).contentType(MediaType.APPLICATION_JSON)
    );
  }

  private ResultActions makeListTCCSubjectsRequestByProfessor(long professorId, String userToken)
    throws Exception {
    return mvc.perform(
      authenticated(get("/api/tcc-subjects?createdBy=" + professorId), userToken)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }
}
