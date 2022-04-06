package com.ufcg.psoft.tccmatch.services.users.professors;

import com.ufcg.psoft.tccmatch.exceptions.users.professors.LaboratoriesNotProvidedException;
import com.ufcg.psoft.tccmatch.services.users.UserValidator;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ProfessorValidator extends UserValidator {

  public Set<String> validateLaboratories(Set<String> laboratories) {
    boolean isValid = laboratories != null;
    if (isValid) return laboratories;
    throw new LaboratoriesNotProvidedException();
  }

  public int validateGuidanceQuota(Integer guidanceQuota) {
    boolean isValid = guidanceQuota != null && guidanceQuota >= 0;
    if (isValid) return guidanceQuota;
    throw new InvalidGuidanceQuotaException();
  }
}
