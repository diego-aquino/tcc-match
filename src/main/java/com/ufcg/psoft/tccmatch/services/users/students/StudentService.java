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

  public Student createStudent(CreateStudentDTO createStudentDTO) {
    String encodedPassword = authenticationService.encodePassword(createStudentDTO.getPassword());

    Student student = new Student(
      createStudentDTO.getEmail(),
      encodedPassword,
      createStudentDTO.getName(),
      createStudentDTO.getRegistryNumber(),
      createStudentDTO.getCompletionPeriod()
    );
    userRepository.save(student);

    return student;
  }
}
