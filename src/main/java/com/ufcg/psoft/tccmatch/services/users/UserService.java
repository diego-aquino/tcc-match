package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.exceptions.users.EmailAlreadyInUseException;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService<GenericUser extends User> {

  @Autowired
  private UserRepository<GenericUser> userRepository;

  @Autowired
  private UserValidator userValidator;

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

  public void updateEmailIfProvided(Optional<String> optionalEmail, User user) {
    if (optionalEmail.isEmpty()) return;

    String newEmail = userValidator.validateEmail(optionalEmail.get());
    ensureEmailIsNotInUse(newEmail, user.getId());
    user.setEmail(newEmail);
  }

  public void updateNameIfProvided(Optional<String> optionalName, User user) {
    if (optionalName.isEmpty()) return;

    String newName = userValidator.validateName(optionalName.get());
    user.setName(newName);
  }

  public Optional<GenericUser> findUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<GenericUser> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
//  public void selectFieldOfStudy(Student user, FieldOfStudy fieldOfStudy) {
//     // User user = this.findUserById(userId).get();
//     user.addField(fieldOfStudy);
//     userRepository.save((GenericUser)user);
//   } 
//   public void selectFieldOfStudy(Professor user, FieldOfStudy fieldOfStudy) {
//     // User user = this.findUserById(userId).get();
//     user.addField(fieldOfStudy);
//     userRepository.save((GenericUser)user);
//   } 
}
