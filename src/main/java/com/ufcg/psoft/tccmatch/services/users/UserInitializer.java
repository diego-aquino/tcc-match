package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.dto.users.CreateCoordinatorDTO;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements ApplicationRunner {

  @Value("${users.default-coordinator.email}")
  private String defaultCoordinatorEmail;

  @Value("${users.default-coordinator.password}")
  private String defaultCoordinatorPassword;

  @Autowired
  private UserService userService;

  @Autowired
  private CoordinatorService coordinatorService;

  @Override
  public void run(ApplicationArguments arguments) throws Exception {
    createDefaultCoordinator();
  }

  private void createDefaultCoordinator() {
    Optional<User> existingCoordinator = userService.findUserByEmail(defaultCoordinatorEmail);

    if (existingCoordinator.isPresent()) return;

    coordinatorService.createCoordinator(
      new CreateCoordinatorDTO(defaultCoordinatorEmail, defaultCoordinatorPassword)
    );
  }
}
