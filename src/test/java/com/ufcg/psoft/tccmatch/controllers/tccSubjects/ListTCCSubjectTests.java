package com.ufcg.psoft.tccmatch.controllers.tccSubjects;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
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

  @Autowired
  private FieldsOfStudyService fieldsOfStudyService;

  private List<TCCSubject> tccSubjects;

  @BeforeEach
  void beforeEach() {
    professor = createMockProfessor();
    student = createMockStudent();

    tccSubjects =
      List.of(
        tccSubjectService.createTCCSubject(
          new CreateTCCSubjectRequestDTO(
            tccSubjectTitle,
            tccSubjectDescription,
            tccSubjectStatus,
            fieldsOfStudyService.mapToIdSet(tccSubjectFieldsOfStudy)
          ),
          professor
        ),
        tccSubjectService.createTCCSubject(
          new CreateTCCSubjectRequestDTO(
            tccSubjectTitle + " 2",
            tccSubjectDescription + " 2",
            tccSubjectStatus + " 2",
            fieldsOfStudyService.mapToIdSet(tccSubjectFieldsOfStudy)
          ),
          professor
        ),
        tccSubjectService.createTCCSubject(
          new CreateTCCSubjectRequestDTO(
            tccSubjectTitle + " 3",
            tccSubjectDescription + " 3",
            tccSubjectStatus + " 3",
            fieldsOfStudyService.mapToIdSet(tccSubjectFieldsOfStudy)
          ),
          professor
        ),
        tccSubjectService.createTCCSubject(
          new CreateTCCSubjectRequestDTO(
            tccSubjectTitle + " 4",
            tccSubjectDescription + " 4",
            tccSubjectStatus + " 4",
            fieldsOfStudyService.mapToIdSet(tccSubjectFieldsOfStudy)
          ),
          student
        ),
        tccSubjectService.createTCCSubject(
          new CreateTCCSubjectRequestDTO(
            tccSubjectTitle + " 5",
            tccSubjectDescription + " 5",
            tccSubjectStatus + " 5",
            fieldsOfStudyService.mapToIdSet(tccSubjectFieldsOfStudy)
          ),
          student
        )
      );
  }

  @Test
  void listTCCSubjectsCreatedByProfessors() throws Exception {
    String studentToken = loginWithMockStudent();

    makeListTCCSubjectsRequest(studentToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(3)))
      .andExpect(jsonPath("$.[0].id", is(tccSubjects.get(0).getId().intValue())))
      .andExpect(jsonPath("$.[0].title", is(tccSubjects.get(0).getTitle())))
      .andExpect(jsonPath("$.[0].description", is(tccSubjects.get(0).getDescription())))
      .andExpect(jsonPath("$.[0].status", is(tccSubjects.get(0).getStatus().toString())))
      .andExpect(
        jsonPath("$.[0].fieldsOfStudy", is(List.copyOf(tccSubjects.get(0).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[0].createdBy", is(tccSubjects.get(0).getCreatedBy().getId().intValue()))
      )
      .andExpect(jsonPath("$.[1].id", is(tccSubjects.get(1).getId().intValue())))
      .andExpect(jsonPath("$.[1].title", is(tccSubjects.get(1).getTitle())))
      .andExpect(jsonPath("$.[1].description", is(tccSubjects.get(1).getDescription())))
      .andExpect(jsonPath("$.[1].status", is(tccSubjects.get(1).getStatus().toString())))
      .andExpect(
        jsonPath("$.[1].fieldsOfStudy", is(List.copyOf(tccSubjects.get(1).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[1].createdBy", is(tccSubjects.get(1).getCreatedBy().getId().intValue()))
      )
      .andExpect(jsonPath("$.[2].id", is(tccSubjects.get(2).getId().intValue())))
      .andExpect(jsonPath("$.[2].title", is(tccSubjects.get(2).getTitle())))
      .andExpect(jsonPath("$.[2].description", is(tccSubjects.get(2).getDescription())))
      .andExpect(jsonPath("$.[2].status", is(tccSubjects.get(2).getStatus().toString())))
      .andExpect(
        jsonPath("$.[2].fieldsOfStudy", is(List.copyOf(tccSubjects.get(2).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[2].createdBy", is(tccSubjects.get(2).getCreatedBy().getId().intValue()))
      );
  }

  @Test
  void listTCCSubjectsCreatedByStudents() throws Exception {
    String professorToken = loginWithMockProfessor();

    makeListTCCSubjectsRequest(professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$.[0].id", is(tccSubjects.get(3).getId().intValue())))
      .andExpect(jsonPath("$.[0].title", is(tccSubjects.get(3).getTitle())))
      .andExpect(jsonPath("$.[0].description", is(tccSubjects.get(3).getDescription())))
      .andExpect(jsonPath("$.[0].status", is(tccSubjects.get(3).getStatus().toString())))
      .andExpect(
        jsonPath("$.[0].fieldsOfStudy", is(List.copyOf(tccSubjects.get(3).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[0].createdBy", is(tccSubjects.get(3).getCreatedBy().getId().intValue()))
      )
      .andExpect(jsonPath("$.[1].id", is(tccSubjects.get(4).getId().intValue())))
      .andExpect(jsonPath("$.[1].title", is(tccSubjects.get(4).getTitle())))
      .andExpect(jsonPath("$.[1].description", is(tccSubjects.get(4).getDescription())))
      .andExpect(jsonPath("$.[1].status", is(tccSubjects.get(4).getStatus().toString())))
      .andExpect(
        jsonPath("$.[1].fieldsOfStudy", is(List.copyOf(tccSubjects.get(4).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[1].createdBy", is(tccSubjects.get(4).getCreatedBy().getId().intValue()))
      );
  }

  @Test
  void listTCCSubjectsCreatedByProfessor() throws Exception {
    String professorToken = loginWithMockProfessor();

    makeListTCCSubjectsRequestByProfessor(professor.getId(), professorToken)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(3)))
      .andExpect(jsonPath("$.[0].id", is(tccSubjects.get(0).getId().intValue())))
      .andExpect(jsonPath("$.[0].title", is(tccSubjects.get(0).getTitle())))
      .andExpect(jsonPath("$.[0].description", is(tccSubjects.get(0).getDescription())))
      .andExpect(jsonPath("$.[0].status", is(tccSubjects.get(0).getStatus().toString())))
      .andExpect(
        jsonPath("$.[0].fieldsOfStudy", is(List.copyOf(tccSubjects.get(0).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[0].createdBy", is(tccSubjects.get(0).getCreatedBy().getId().intValue()))
      )
      .andExpect(jsonPath("$.[1].id", is(tccSubjects.get(1).getId().intValue())))
      .andExpect(jsonPath("$.[1].title", is(tccSubjects.get(1).getTitle())))
      .andExpect(jsonPath("$.[1].description", is(tccSubjects.get(1).getDescription())))
      .andExpect(jsonPath("$.[1].status", is(tccSubjects.get(1).getStatus().toString())))
      .andExpect(
        jsonPath("$.[1].fieldsOfStudy", is(List.copyOf(tccSubjects.get(1).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[1].createdBy", is(tccSubjects.get(1).getCreatedBy().getId().intValue()))
      )
      .andExpect(jsonPath("$.[2].id", is(tccSubjects.get(2).getId().intValue())))
      .andExpect(jsonPath("$.[2].title", is(tccSubjects.get(2).getTitle())))
      .andExpect(jsonPath("$.[2].description", is(tccSubjects.get(2).getDescription())))
      .andExpect(jsonPath("$.[2].status", is(tccSubjects.get(2).getStatus().toString())))
      .andExpect(
        jsonPath("$.[2].fieldsOfStudy", is(List.copyOf(tccSubjects.get(2).getFieldsOfStudy())))
      )
      .andExpect(
        jsonPath("$.[2].createdBy", is(tccSubjects.get(2).getCreatedBy().getId().intValue()))
      );
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
