package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.dto.users.CreateCoordinatorDTO;
import com.ufcg.psoft.tccmatch.models.users.Coordinator;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  public Coordinator createCoordinator(CreateCoordinatorDTO createCoordinatorDTO) {
    String encodedPassword = authenticationService.encodePassword(
      createCoordinatorDTO.getPassword()
    );

    Coordinator coordinator = new Coordinator(createCoordinatorDTO.getEmail(), encodedPassword);
    userRepository.save(coordinator);

    return coordinator;
  }
}
