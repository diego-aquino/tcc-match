package com.ufcg.psoft.tccmatch.services.users.students;

import com.ufcg.psoft.tccmatch.exceptions.users.students.InvalidRegistryNumberException;
import com.ufcg.psoft.tccmatch.services.users.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class StudentValidator extends UserValidator {

  private static final String REGISTRY_NUMBER_PATTERN = "^\\d{9}$";

  public String validateRegistryNumber(String registryNumber) {
    boolean isValidRegistryNumber = matchesPattern(registryNumber, REGISTRY_NUMBER_PATTERN);
    if (isValidRegistryNumber) return registryNumber.trim();
    throw new InvalidRegistryNumberException();
  }
}
