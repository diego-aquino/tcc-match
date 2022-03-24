package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Iterator;
import java.util.Set;
import org.springframework.http.MediaType;
import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.exceptions.fieldsOfStudy.FieldNotFoundException;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class FieldsOfStudySelectionTest extends IntegrationTests {

  private String studentToken;
  private String nameField1 = "field1";
  private String nameField2 = "field2";
  private Student student;
  private Professor professor;
  private String professorToken;
  private FieldOfStudy field;
  private Long idField;
  private FieldOfStudy field2;
  private Long idField2;

  @Autowired
  private FieldsOfStudyService fieldsOfStudyService;

  @Autowired
  private UserService<User> userService;

  @BeforeEach
  void beforeEach() {
    field = fieldsOfStudyService.createFieldsOfStudy(nameField1);
    idField = field.getId();
    field2 = fieldsOfStudyService.createFieldsOfStudy(nameField2);
    idField2 = field2.getId();
  }

  @Test
  void errorFieldNotFound() throws Exception {
    student = createMockStudent();
    studentToken = loginWithMockStudent();
    selectFieldOfStudyRequest(123L, studentToken)
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is(FieldNotFoundException.message())));
  }
  @Test
  void validSelectFieldStudent() throws Exception {
    student = createMockStudent();
    studentToken = loginWithMockStudent();
    selectFieldOfStudyRequest(idField, studentToken)
      .andExpect(status().isOk());
      
    Set<FieldOfStudy>fields = ((Student)userService.findUserById(student.getId()).get()).getFields();
    assertEquals(1,fields.size());
    Iterator<FieldOfStudy> iterator = fields.iterator();
    FieldOfStudy fielder = iterator.next();
    assertEquals(fielder.getId(),field.getId());
    assertEquals(fielder.getName(),field.getName());
  }
  
  @Test
  void validSelectMultipleFieldStudent() throws Exception {
    student = createMockStudent();
    studentToken = loginWithMockStudent();
    selectFieldOfStudyRequest(idField, studentToken)
      .andExpect(status().isOk());
      
    Set<FieldOfStudy>fields = ((Student)userService.findUserById(student.getId()).get()).getFields();
    assertEquals(1,fields.size());
    Iterator<FieldOfStudy> iterator = fields.iterator();
    FieldOfStudy fielder = iterator.next();
    assertEquals(fielder.getId(),field.getId());
    assertEquals(fielder.getName(),field.getName());

    selectFieldOfStudyRequest(idField2, studentToken)
        .andExpect(status().isOk());
    fields = ((Student)userService.findUserById(student.getId()).get()).getFields();
    assertEquals(2,fields.size());
  }
  @Test
  void validSelectMultipleEqualFieldStudent() throws Exception {
    student = createMockStudent();
    studentToken = loginWithMockStudent();
    selectFieldOfStudyRequest(idField, studentToken)
      .andExpect(status().isOk());
    
    Set<FieldOfStudy>fields = ((Student)userService.findUserById(student.getId()).get()).getFields();
    assertEquals(1,fields.size());
    Iterator<FieldOfStudy> iterator = fields.iterator();
    FieldOfStudy fielder = iterator.next();
    assertEquals(fielder.getId(),field.getId());
    assertEquals(fielder.getName(),field.getName());

    selectFieldOfStudyRequest(idField, studentToken)
        .andExpect(status().isOk());
    fields = ((Student)userService.findUserById(student.getId()).get()).getFields();
    assertEquals(1,fields.size());
  }
  
  @Test
  void validSelectFieldProfessor() throws Exception {
    professor = createMockProfessor();
    professorToken = loginWithMockProfessor();
    
    selectFieldOfStudyRequest(idField, professorToken)
        .andExpect(status().isOk());
      
    Set<FieldOfStudy>fields = ((Professor)userService.findUserById(professor.getId()).get()).getFields();
    assertEquals(1,fields.size());

    Iterator<FieldOfStudy> iterator = fields.iterator();
    FieldOfStudy fielder = iterator.next();
    assertEquals(fielder.getId(),field.getId());
    assertEquals(fielder.getName(),field.getName());

  }
  private ResultActions selectFieldOfStudyRequest(Long idField, String studentToken) throws Exception{
    String endpoint = String.format("/api/fields-of-study/select/%d", idField);
    return mvc.perform(
        authenticated(post(endpoint),studentToken)
        .contentType(MediaType.APPLICATION_JSON)
        );
  }
}