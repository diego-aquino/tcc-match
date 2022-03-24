package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FieldsOfStudyListProfessorsTest extends IntegrationTests {

  @Autowired
  private FieldsOfStudyService fieldsOfStudyService;

  @Autowired
  private ProfessorService professorService;

  private Student student;
  private Professor professor1;
  private Professor professor2;
  private Professor professor3;
  private String professorEmail1 = "professor1@email.com";
  private String professorEmail2 = "professor2@email.com";
  private String professorEmail3 = "professor3@email.com";
  private String studentToken;

  @BeforeEach
  void beforeEach() {
    FieldOfStudy field1 = fieldsOfStudyService.createFieldsOfStudy("field1");
    FieldOfStudy field2 = fieldsOfStudyService.createFieldsOfStudy("field2");
    FieldOfStudy field3 = fieldsOfStudyService.createFieldsOfStudy("field3");

    student = createMockStudent();
    studentToken = loginWithMockStudent();

    professor1 = createProfessorDTO(professorEmail1);
    professor2 = createProfessorDTO(professorEmail2);
    professor3 = createProfessorDTO(professorEmail3);

    fieldsOfStudyService.selectFieldOfStudy(professor1, field1);
    fieldsOfStudyService.selectFieldOfStudy(
      professorService.findByIdOrThrow(professor1.getId()),
      field2
    );

    fieldsOfStudyService.selectFieldOfStudy(professor2, field3);
    fieldsOfStudyService.selectFieldOfStudy(professor3, field3);

    fieldsOfStudyService.selectFieldOfStudy(student, field3);
  }

  Professor createProfessorDTO(String professorEmail) {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      "12345678",
      "name",
      new HashSet<>()
    );
    return professorService.createProfessor(createProfessorDTO);
  }

  @Test
  void validProfessorList() throws Exception {
    ListFieldOfStudyRequest()
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$.[0].id", is(professor2.getId().intValue())))
      .andExpect(jsonPath("$.[1].id", is(professor3.getId().intValue())));
  }

  @Test
  void ZeroMatching() throws Exception {
    professorService.removeProfessor(professor3.getId());
    professorService.removeProfessor(professor2.getId());
    ListFieldOfStudyRequest().andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
  }

  private ResultActions ListFieldOfStudyRequest() throws Exception {
    return mvc.perform(
      authenticated(get("/api/fields-of-study/professors"), studentToken)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }
}
