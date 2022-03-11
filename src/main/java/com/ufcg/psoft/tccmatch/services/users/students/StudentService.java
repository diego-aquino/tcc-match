package com.ufcg.psoft.tccmatch.services.users.students;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private StudentValidator studentValidator;

  public Student createStudent(CreateStudentDTO createStudentDTO) {
    String email = studentValidator.validateEmail(createStudentDTO.getEmail());
    String rawPassword = studentValidator.validatePassword((createStudentDTO.getPassword()));
    String name = studentValidator.validateName(createStudentDTO.getName());
    String registryNumber = studentValidator.validateRegistryNumber(
      createStudentDTO.getRegistryNumber()
    );
    String completionPeriod = studentValidator.validateCompletionPeriod(
      createStudentDTO.getCompletionPeriod()
    );

    String encodedPassword = authenticationService.encodePassword(rawPassword);

    Student student = new Student(email, encodedPassword, name, registryNumber, completionPeriod);
    userRepository.save(student);

    return student;
  }
}
