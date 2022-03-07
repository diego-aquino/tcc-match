package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.dto.users.CreateUserRequestDTO;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  public User createUser(CreateUserRequestDTO createUserDTO) {
    String email = createUserDTO.getEmail();
    String encodedPassword = authenticationService.encodePassword(createUserDTO.getPassword());

    User user = new User(email, encodedPassword);
    userRepository.save(user);
    return user;
  }

  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
