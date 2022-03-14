package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.exceptions.api.ConflictApiException;
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
    Optional<GenericUser> existingStudent = userRepository.findByEmail(email);

    if (existingStudent.isPresent()) {
      throw new ConflictApiException("Email already in use.");
    }
  }

  public Optional<GenericUser> findUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<GenericUser> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
