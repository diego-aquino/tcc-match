package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Iterator;
import java.util.Set;

import org.springframework.http.MediaType;
import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class FieldsOfStudySelectionTest extends IntegrationTests {
    private String name = "field";
    private Student student;
    protected String studentEmail = "student@email.com";
    protected String studentRawPassword = "12345678";
    protected String studentName = "Student";
    protected String studentRegistryNumber = "111000111";
    protected String studentCompletionPeriod = "2024.1";
    private String studentToken;

    @Autowired
    private StudentService studentService;
    @Autowired
    private FieldsOfStudyService fieldsOfStudyService;
    @Autowired
    private UserService<User> userService;
    @BeforeEach
    void beforeEach() {
      CreateStudentDTO createStudentDTO = new CreateStudentDTO(
        studentEmail,
        studentRawPassword,
        studentName,
        studentRegistryNumber,
        studentCompletionPeriod
      );
  
      student = studentService.createStudent(createStudentDTO);
      studentToken = loginProgrammatically(studentEmail, studentRawPassword);
    }
    @Test
    void validSelectField() throws Exception {
        FieldOfStudy field = fieldsOfStudyService.createFieldsOfStudy(name);
        Long idField = field.getId();
        selectFieldOfStudyRequest(idField, studentToken)
            .andExpect(status().isOk());
         
        // fieldsOfStudyService.selectFieldOfStudy(student, field);
        Set<FieldOfStudy>fields = userService.findUserById(student.getId()).get().getFields();
        assertEquals(fields.size(),1);
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
