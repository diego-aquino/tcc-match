package com.ufcg.psoft.tccmatch.controllers.tccGuidanceProblem;

import java.util.List;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.CreateTCCGuidanceProblemDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.ListTCCGuidanceProblemResponseDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem.TCCGuidanceProblemResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccGuidanceProblem.TCCGuidanceProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tcc-guidance-problems")
public class TCCGuidanceProblemController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private TCCGuidanceProblemService tccGuidanceProblemService;

  @PostMapping
  public ResponseEntity<TCCGuidanceProblemResponseDTO> createTCCGuidanceProblem(
      @RequestBody CreateTCCGuidanceProblemDTO createTCCGuidanceProblemDTO) {
    authenticationService.ensureUserTypes(User.Type.STUDENT, User.Type.PROFESSOR);

    User createdBy = authenticationService.getAuthenticatedUser();

    TCCGuidanceProblem tccGuidanceProblem = tccGuidanceProblemService
        .createTCCGuidanceProblem(createTCCGuidanceProblemDTO, createdBy);
    return new ResponseEntity<>(new TCCGuidanceProblemResponseDTO(tccGuidanceProblem), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<ListTCCGuidanceProblemResponseDTO> listTCCGuidanceProblems() {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    List<TCCGuidanceProblem> tccGuidanceProblems = tccGuidanceProblemService.listTCCGuidanceProblems();
    List<TCCGuidanceProblemResponseDTO> tccGuidanceProblemsOfStudents = TCCGuidanceProblemResponseDTO
        .fromTCCGuidanceProblems(tccGuidanceProblemService
            .listAllTCCGuidanceProblemsOfStudents(tccGuidanceProblems));
    List<TCCGuidanceProblemResponseDTO> tccGuidanceProblemsOfProfessors = TCCGuidanceProblemResponseDTO
        .fromTCCGuidanceProblems(tccGuidanceProblemService
            .listAllTCCGuidanceProblemsOfProfessors(tccGuidanceProblems));

    return new ResponseEntity<>(
        new ListTCCGuidanceProblemResponseDTO(tccGuidanceProblemsOfStudents, tccGuidanceProblemsOfProfessors),
        HttpStatus.OK);
  }
}
