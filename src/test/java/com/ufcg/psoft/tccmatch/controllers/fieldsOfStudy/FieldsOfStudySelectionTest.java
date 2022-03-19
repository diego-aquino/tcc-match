package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.http.MediaType;
import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class FieldsOfStudySelectionTest extends IntegrationTests {
    private String studentEmail = "student@email.com";
    private String professorEmail = "professor@email.com";
    private String studentRegistryNumber = "111000111";
    private String studentCompletionPeriod = "2024.1";
    private Set<String> laboratories = new HashSet<>();
    private String rawPassword = "12345678";
    private String name = "name";
    
    private String studentToken;
    private String nameField = "field";
    private Student student;
    private Professor professor;
    private String professorToken;
    private FieldOfStudy field;
    private Long idField;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private FieldsOfStudyService fieldsOfStudyService;

    @Autowired
    private UserService<User> userService;



    @BeforeEach
    void beforeEach() {
      field = fieldsOfStudyService.createFieldsOfStudy(nameField);
      idField = field.getId();
    }

    void createStudentDTO(){
      CreateStudentDTO createStudentDTO = new CreateStudentDTO(
        studentEmail,
        rawPassword,
        name,
        studentRegistryNumber,
        studentCompletionPeriod
      );
  
      student = studentService.createStudent(createStudentDTO);
      studentToken = loginProgrammatically(studentEmail, rawPassword);
    }
    
    void createProfessorDTO(){
      CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
        professorEmail,
        rawPassword,
        name,
        laboratories
      );
      professor = professorService.createProfessor(createProfessorDTO);
      professorToken = loginProgrammatically(professorEmail,rawPassword);
    }

    @Test
    void invalidSelectFieldId() throws Exception {
      createStudentDTO();
        selectFieldOfStudyRequest(123L, studentToken)
            .andExpect(status().isBadRequest());
    }
    @Test
    void validSelectFieldStudent() throws Exception {
      createStudentDTO();
        selectFieldOfStudyRequest(idField, studentToken)
            .andExpect(status().isOk());
         
        Set<FieldOfStudy>fields = userService.findUserById(student.getId()).get().getFields();
        assertEquals(1,fields.size());
        Iterator<FieldOfStudy> iterator = fields.iterator();
        FieldOfStudy fielder = iterator.next();
        assertEquals(fielder.getId(),field.getId());
        assertEquals(fielder.getName(),field.getName());

    }
    @Test
    void validSelectFieldProfessor() throws Exception {
      createProfessorDTO();
        selectFieldOfStudyRequest(idField, professorToken)
            .andExpect(status().isOk());
         
        Set<FieldOfStudy>fields = userService.findUserById(professor.getId()).get().getFields();
        assertEquals(1,fields.size());

        Iterator<FieldOfStudy> iterator = fields.iterator();
        FieldOfStudy fielder = iterator.next();
        assertEquals(fielder.getId(),field.getId());
        assertEquals(fielder.getName(),field.getName());

    }
    private ResultActions selectFieldOfStudyRequest(Long idField, String studentToken) throws Exception{
        String endpoint = String.format("/api/fields-of-study/select/%d", idField);
        return mvc.perform(
            authenticated(patch(endpoint),studentToken)
            .contentType(MediaType.APPLICATION_JSON)
            );
    }
}
