package com.ufcg.psoft.tccmatch.services.users.professors;

import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;
import com.ufcg.psoft.tccmatch.services.users.UserValidator;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ProfessorValidator extends UserValidator {

  public Set<String> validateLaboratories(Set<String> laboratories) {
    boolean isValid = laboratories != null;
    if (isValid) return laboratories;
    throw new BadRequestApiException("Laboratories not provided.");
  }
}
