package com.ufcg.psoft.tccmatch.services.tccGuidanceProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.CreateTCCGuidanceProblemDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidances.TCCGuidanceNotFoundException;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccGuidanceProblem.TCCGuidanceProblemRepository;
import com.ufcg.psoft.tccmatch.repositories.tccGuidances.TCCGuidanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCGuidanceProblemService {
  @Autowired
  private TCCGuidanceProblemRepository tccGuidanceProblemRepository;

  @Autowired
  private TCCGuidanceRepository tccGuidanceRepository;

  @Autowired
  private TCCGuidanceProblemValidator tccGuidanceProblemValidator;

  public TCCGuidanceProblem createTCCGuidanceProblem(CreateTCCGuidanceProblemDTO tccGuidanceProblemDTO,
      User createdBy) {
    Category category = tccGuidanceProblemValidator.validateCategory(tccGuidanceProblemDTO.getCategory());
    Optional<TCCGuidance> optionalTCCGuidance = tccGuidanceRepository
        .findById(tccGuidanceProblemDTO.getTccGuidanceId());
    if (optionalTCCGuidance.isEmpty())
      throw new TCCGuidanceNotFoundException();
    TCCGuidance tccGuidance = optionalTCCGuidance.get();

    TCCGuidanceProblem tccGuidanceProblem = new TCCGuidanceProblem(category,
        tccGuidanceProblemDTO.getDescription(), createdBy, tccGuidance);
    tccGuidanceProblemRepository.save(tccGuidanceProblem);
    return tccGuidanceProblem;
  }

  public List<TCCGuidanceProblem> listTCCGuidanceProblems() {
    return tccGuidanceProblemRepository.findAll();
  }

  public List<TCCGuidanceProblem> listAllTCCGuidanceProblemsOfStudents(List<TCCGuidanceProblem> tccGuidanceProblems) {
    List<TCCGuidanceProblem> studentTCCGuidanceProblems = new ArrayList<TCCGuidanceProblem>();

    for (TCCGuidanceProblem tccGuidanceProblem : tccGuidanceProblems) {
      if (tccGuidanceProblem.getCreatedBy().getType() == User.Type.STUDENT) {
        studentTCCGuidanceProblems.add(tccGuidanceProblem);
      }
    }

    return studentTCCGuidanceProblems;
  }

  public List<TCCGuidanceProblem> listAllTCCGuidanceProblemsOfProfessors(List<TCCGuidanceProblem> tccGuidanceProblems) {
    List<TCCGuidanceProblem> professorTCCGuidanceProblems = new ArrayList<TCCGuidanceProblem>();

    for (TCCGuidanceProblem tccGuidanceProblem : tccGuidanceProblems) {
      if (tccGuidanceProblem.getCreatedBy().getType() == User.Type.PROFESSOR) {
        professorTCCGuidanceProblems.add(tccGuidanceProblem);
      }
    }

    return professorTCCGuidanceProblems;
  }

}
