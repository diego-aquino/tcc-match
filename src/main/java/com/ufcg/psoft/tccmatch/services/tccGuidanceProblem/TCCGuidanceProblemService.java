package com.ufcg.psoft.tccmatch.services.tccGuidanceProblem;

import java.util.Optional;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.CreateTCCGuidanceProblemDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.UserNotFoundException;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccGuidanceProblem.TCCGuidanceProblemRepository;
import com.ufcg.psoft.tccmatch.services.users.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCGuidanceProblemService {
  @Autowired
  private TCCGuidanceProblemRepository tccGuidanceProblemRepository;

  @Autowired
  private UserService<User> userService;

  @Autowired
  private TCCGuidanceProblemValidator tccGuidanceProblemValidator;

  public TCCGuidanceProblem createTCCGuidanceProblem(CreateTCCGuidanceProblemDTO tccGuidanceProblemDTO) {
    Optional<User> optionalUser = userService.findUserById(tccGuidanceProblemDTO.getUserWhoCreatedId());
    if (optionalUser.isEmpty())
      throw new UserNotFoundException();
    User createdBy = optionalUser.get();
    Category category = tccGuidanceProblemValidator.validateCategory(tccGuidanceProblemDTO.getCategory());

    TCCGuidanceProblem tccGuidanceProblem = new TCCGuidanceProblem(category,
        tccGuidanceProblemDTO.getDescription(), createdBy);
    tccGuidanceProblemRepository.save(tccGuidanceProblem);
    return tccGuidanceProblem;
  }

}
