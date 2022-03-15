package com.ufcg.psoft.tccmatch.controllers.users;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.dto.users.StudentResponseDTO;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/students")
public class StudentsController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private StudentService studentService;

  @PostMapping
  public ResponseEntity<StudentResponseDTO> createStudent(
    @RequestBody CreateStudentDTO createStudentDTO
  ) {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    Student student = studentService.createStudent(createStudentDTO);
    return new ResponseEntity<>(new StudentResponseDTO(student), HttpStatus.CREATED);
  }
}
