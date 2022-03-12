package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.exceptions.users.EmailAlreadyInUseException;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService<GenericUser extends User> {

  @Autowired
  private UserRepository<GenericUser> userRepository;

  public void ensureEmailIsNotInUse(String email) {
    ensureEmailIsNotInUse(email, null);
  }

  public void ensureEmailIsNotInUse(String email, Long ignoreUserId) {
    Optional<GenericUser> existingUser = userRepository.findByEmail(email);

    if (existingUser.isEmpty()) return;

    Long existingUserId = existingUser.get().getId();

    if (!existingUserId.equals(ignoreUserId)) {
      throw new EmailAlreadyInUseException();
    }
  }

  public Optional<GenericUser> findUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<GenericUser> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
