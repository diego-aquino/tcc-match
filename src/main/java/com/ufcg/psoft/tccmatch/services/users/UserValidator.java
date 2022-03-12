package com.ufcg.psoft.tccmatch.services.users;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;
import com.ufcg.psoft.tccmatch.services.Validator;
import org.springframework.stereotype.Service;

@Service
public class UserValidator extends Validator {

  private static final String EMAIL_ADDRESS_PATTERN = "^\\S+@\\S+\\.\\S+$";
  private static final int MIN_PASSWORD_LENGTH = 8;

  public String validateEmail(String email) {
    boolean isValidEmail = matchesPattern(email, EMAIL_ADDRESS_PATTERN);
    if (isValidEmail) return email.trim();
    throw new BadRequestApiException("Invalid email.");
  }

  public String validatePassword(String password) {
    boolean isValidPassword = password != null && password.trim().length() >= MIN_PASSWORD_LENGTH;
    if (isValidPassword) return password.trim();
    throw new BadRequestApiException("Password too short.");
  }

  public String validateName(String name) {
    boolean isValidName = name != null && !name.isBlank();
    if (isValidName) return name;
    throw new BadRequestApiException("User name is empty.");
  }
}
