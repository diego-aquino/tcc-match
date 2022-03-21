package com.ufcg.psoft.tccmatch.controllers.tccSubjects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TCCSubjectCreationTests extends IntegrationTests {

  private String TCCSubjectTitle = "IA: Salvadora da terra, ou fim dos tempos?";
  private String TCCSubjectDescription =
    "Um estudo sobre as diversas implicações do acanço de IA na tecnologia.";
  private String TCCSubjectStatus = "Nas etapas finais...";
  private Set<FieldOfStudy> TCCSubjectFieldsOfStudy = new HashSet<FieldOfStudy>();

  @Autowired
  private TCCSubjectService tccSubjectService;

  @Test
  void TCCSubjectCreationByStudent() throws Exception {
    createMockStudent();
    String studentToken = loginProgrammaticallyWithMockStudent();

    CreateTCCSubjectRequestDTO createTCCSubjectRequestDTO = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle,
      TCCSubjectDescription,
      TCCSubjectStatus,
      TCCSubjectFieldsOfStudy
    );

    makeCreateTCCSubjectRequest(createTCCSubjectRequestDTO, studentToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.title", is(TCCSubjectTitle)))
      .andExpect(jsonPath("$.description", is(TCCSubjectDescription)))
      .andExpect(jsonPath("$.status", is(TCCSubjectStatus)))
      .andExpect(jsonPath("$.fieldsOfStudy", is(List.copyOf(TCCSubjectFieldsOfStudy))))
      .andExpect(jsonPath("$.createdBy", is(mockStudent.getId().intValue())));

    Optional<TCCSubject> optionalTCCSubjectCreated = tccSubjectService.findTCCSubjectByTitle(
      TCCSubjectTitle
    );
    assertTrue(optionalTCCSubjectCreated.isPresent());

    TCCSubject tccSubjctCreated = optionalTCCSubjectCreated.get();

    assertEquals(TCCSubjectTitle, tccSubjctCreated.getTitle());
    assertEquals(TCCSubjectDescription, tccSubjctCreated.getDescription());
    assertEquals(TCCSubjectStatus, tccSubjctCreated.getStatus());
    //assertEquals(TCCSubjectFieldsOfStudy,tccSubjcetCreated.getFieldsOfStudy());
    assertEquals(mockStudent.getId(), tccSubjctCreated.getCreatedBy().getId().intValue());
    assertEquals(User.Type.STUDENT, tccSubjctCreated.getCreatedBy().getType());
  }

  @Test
  void TCCSubjectCreationByProfessor() throws Exception {
    createMockProfessor();
    String professorToken = loginProgrammaticallyWithMockProfessor();

    CreateTCCSubjectRequestDTO createTCCSubjectRequestDTO = new CreateTCCSubjectRequestDTO(
      TCCSubjectTitle,
      TCCSubjectDescription,
      TCCSubjectStatus,
      TCCSubjectFieldsOfStudy
    );

    makeCreateTCCSubjectRequest(createTCCSubjectRequestDTO, professorToken)
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(any(Integer.class))))
      .andExpect(jsonPath("$.title", is(TCCSubjectTitle)))
      .andExpect(jsonPath("$.description", is(TCCSubjectDescription)))
      .andExpect(jsonPath("$.status", is(TCCSubjectStatus)))
      .andExpect(jsonPath("$.fieldsOfStudy", is(List.copyOf(TCCSubjectFieldsOfStudy))))
      .andExpect(jsonPath("$.createdBy", is(mockProfessor.getId().intValue())));

    Optional<TCCSubject> optionalTCCSubjectCreated = tccSubjectService.findTCCSubjectByTitle(
      TCCSubjectTitle
    );
    assertTrue(optionalTCCSubjectCreated.isPresent());

    TCCSubject tccSubjcetCreated = optionalTCCSubjectCreated.get();

    assertEquals(TCCSubjectTitle, tccSubjcetCreated.getTitle());
    assertEquals(TCCSubjectDescription, tccSubjcetCreated.getDescription());
    assertEquals(TCCSubjectStatus, tccSubjcetCreated.getStatus());
    //assertEquals(TCCSubjectFieldsOfStudy,tccSubjcetCreated.getFieldsOfStudy());
    assertEquals(mockProfessor.getId(), tccSubjcetCreated.getCreatedBy().getId());
    assertEquals(User.Type.PROFESSOR, tccSubjcetCreated.getCreatedBy().getType());
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
