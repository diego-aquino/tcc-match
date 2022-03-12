package com.ufcg.psoft.tccmatch.services.users.coordinators;

import com.ufcg.psoft.tccmatch.dto.users.CreateCoordinatorDTO;
import com.ufcg.psoft.tccmatch.models.users.Coordinator;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import com.ufcg.psoft.tccmatch.services.users.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService {

  @Autowired
  private UserService<Coordinator> userService;

  @Autowired
  private UserRepository<Coordinator> userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserValidator userValidator;

  public Coordinator createCoordinator(CreateCoordinatorDTO createCoordinatorDTO) {
    String email = userValidator.validateEmail(createCoordinatorDTO.getEmail());
    String rawPassword = userValidator.validatePassword((createCoordinatorDTO.getPassword()));
    String name = userValidator.validateName((createCoordinatorDTO.getName()));

    userService.ensureEmailIsNotInUse(email);

    String encodedPassword = authenticationService.encodePassword(rawPassword);

    Coordinator coordinator = new Coordinator(email, encodedPassword, name);
    userRepository.save(coordinator);

    return coordinator;
  }
}
