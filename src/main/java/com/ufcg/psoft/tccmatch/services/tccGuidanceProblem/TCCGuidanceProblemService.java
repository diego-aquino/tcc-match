package com.ufcg.psoft.tccmatch.services.tccGuidanceProblem;

import java.util.Optional;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.CreateTCCGuidanceProblemDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidances.TCCGuidanceNotFoundException;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccGuidanceProblem.TCCGuidanceProblemRepository;
import com.ufcg.psoft.tccmatch.repositories.tccGuidances.TCCGuidanceRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCGuidanceProblemService {
  @Autowired
  private TCCGuidanceProblemRepository tccGuidanceProblemRepository;

  @Autowired
  private TCCGuidanceRepository tccGuidanceRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private TCCGuidanceProblemValidator tccGuidanceProblemValidator;

  public TCCGuidanceProblem createTCCGuidanceProblem(CreateTCCGuidanceProblemDTO tccGuidanceProblemDTO) {
    Category category = tccGuidanceProblemValidator.validateCategory(tccGuidanceProblemDTO.getCategory());
    Optional<TCCGuidance> optionalTCCGuidance = tccGuidanceRepository
        .findById(tccGuidanceProblemDTO.getTCCGuidanceId());
    if (optionalTCCGuidance.isEmpty())
      throw new TCCGuidanceNotFoundException();
    TCCGuidance tccGuidance = optionalTCCGuidance.get();
    User createdBy = authenticationService.getAuthenticatedUser();

    TCCGuidanceProblem tccGuidanceProblem = new TCCGuidanceProblem(category,
        tccGuidanceProblemDTO.getDescription(), createdBy, tccGuidance);
    tccGuidanceProblemRepository.save(tccGuidanceProblem);
    return tccGuidanceProblem;
  }

}
