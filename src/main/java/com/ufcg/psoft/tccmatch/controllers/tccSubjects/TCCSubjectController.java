package com.ufcg.psoft.tccmatch.controller.tccSubjects;

import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectResponseDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.ListTCCSubjectResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tcc-subjects")
@CrossOrigin
public class TCCSubjectController {

  @Autowired
  TCCSubjectService tccSubjectService;

  @Autowired
  AuthenticationService authenticationService;

  @PostMapping("/")
  public ResponseEntity<CreateTCCSubjectResponseDTO> createTCCSubject(
    @RequestBody CreateTCCSubjectRequestDTO tccSubjectDTO
  ) {
    User user = authenticationService.getAuthenticatedUser();

    TCCSubject newTCCSubject = tccSubjectService.createTCCSubject(tccSubjectDTO, user);

    return new ResponseEntity<CreateTCCSubjectResponseDTO>(
      new CreateTCCSubjectResponseDTO(newTCCSubject),
      HttpStatus.CREATED
    );
  }

  @GetMapping("/")
  public ResponseEntity<ListTCCSubjectResponseDTO> listTCCSubjects(
    @RequestParam("createdBy") long createdById
  ) {
    User user = authenticationService.getAuthenticatedUser();

    Set<TCCSubject> TCCSubjects = tccSubjectService.listTCCSubjects(user, createdById);

    return new ResponseEntity<ListTCCSubjectResponseDTO>(
      new ListTCCSubjectResponseDTO(TCCSubjects),
      HttpStatus.OK
    );
  }
}
