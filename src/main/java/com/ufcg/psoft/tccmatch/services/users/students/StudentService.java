package com.ufcg.psoft.tccmatch.services.users.students;

import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.dto.users.UpdateStudentDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.UserNotFoundException;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private UserService<Student> userService;

  @Autowired
  private UserRepository<Student> userRepository;

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

    userService.ensureEmailIsNotInUse(email);

    String encodedPassword = authenticationService.encodePassword(rawPassword);

    Student student = new Student(email, encodedPassword, name, registryNumber, completionPeriod);
    userRepository.save(student);

    return student;
  }

  public Student updateStudent(Long studentId, UpdateStudentDTO updateStudentDTO) {
    Optional<Student> optionalStudent = userRepository.findById(studentId);

    if (optionalStudent.isEmpty()) {
      throw new UserNotFoundException();
    }

    Student student = optionalStudent.get();
    updateEmailIfProvided(updateStudentDTO.getEmail(), student);
    updateNameIfProvided(updateStudentDTO.getName(), student);
    updateCompletionPeriodIfProvided(updateStudentDTO.getCompletionPeriod(), student);
    userRepository.save(student);

    return student;
  }

  private void updateEmailIfProvided(Optional<String> optionalEmail, Student student) {
    if (optionalEmail.isEmpty()) return;

    String newEmail = studentValidator.validateEmail(optionalEmail.get());
    userService.ensureEmailIsNotInUse(newEmail, student.getId());
    student.setEmail(newEmail);
  }

  private void updateNameIfProvided(Optional<String> optionalName, Student student) {
    if (optionalName.isEmpty()) return;

    String newName = studentValidator.validateName(optionalName.get());
    student.setName(newName);
  }

  private void updateCompletionPeriodIfProvided(
    Optional<String> optionalCompletionPeriod,
    Student student
  ) {
    if (optionalCompletionPeriod.isEmpty()) return;

    String newCompletionPeriod = studentValidator.validateCompletionPeriod(
      optionalCompletionPeriod.get()
    );
    student.setCompletionPeriod(newCompletionPeriod);
  }

  public boolean hasPermissionToUpdateStudent(User authenticatedUser, Long studentId) {
    if (authenticatedUser == null) return false;
    if (authenticatedUser.getType() == User.Type.COORDINATOR) return true;

    return (
      authenticatedUser.getType() == User.Type.STUDENT &&
      authenticatedUser.getId().equals(studentId)
    );
  }
}
