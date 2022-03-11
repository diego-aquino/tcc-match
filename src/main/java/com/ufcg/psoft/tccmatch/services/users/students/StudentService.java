package com.ufcg.psoft.tccmatch.services.users.students;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserValidator userValidator;

  public Student createStudent(CreateStudentDTO createStudentDTO) {
    String email = userValidator.validateEmail(createStudentDTO.getEmail());
    String rawPassword = userValidator.validatePassword((createStudentDTO.getPassword()));

    String encodedPassword = authenticationService.encodePassword(rawPassword);

    Student student = new Student(
      email,
      encodedPassword,
      createStudentDTO.getName(),
      createStudentDTO.getRegistryNumber(),
      createStudentDTO.getCompletionPeriod()
    );
    userRepository.save(student);

    return student;
  }
}
