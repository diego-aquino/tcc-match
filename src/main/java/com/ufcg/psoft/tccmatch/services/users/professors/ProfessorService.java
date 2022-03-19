package com.ufcg.psoft.tccmatch.services.users.professors;

import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

  @Autowired
  private UserService<Professor> userService;

  @Autowired
  private UserRepository<Professor> userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private ProfessorValidator professorValidator;

  public Professor createProfessor(CreateProfessorDTO createProfessorDTO) {
    String email = professorValidator.validateEmail(createProfessorDTO.getEmail());
    String rawPassword = professorValidator.validatePassword((createProfessorDTO.getPassword()));
    String name = professorValidator.validateName(createProfessorDTO.getName());
    Set<String> laboratories = professorValidator.validateLaboratories(
      createProfessorDTO.getLaboratories()
    );

    userService.ensureEmailIsNotInUse(email);

    String encodedPassword = authenticationService.encodePassword(rawPassword);

    Professor professor = new Professor(email, encodedPassword, name, laboratories);
    userRepository.save(professor);

    return professor;
  } 
}
