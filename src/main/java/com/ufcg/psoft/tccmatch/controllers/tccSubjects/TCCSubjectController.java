package com.ufcg.psoft.tccmatch.controllers.tccSubjects;

import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.TCCSubjectResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tcc-subjects")
public class TCCSubjectController {

  @Autowired
  TCCSubjectService tccSubjectService;

  @Autowired
  AuthenticationService authenticationService;

  @PostMapping
  public ResponseEntity<TCCSubjectResponseDTO> createTCCSubject(
    @RequestBody CreateTCCSubjectRequestDTO tccSubjectDTO
  ) {
    authenticationService.ensureUserTypes(User.Type.PROFESSOR, User.Type.STUDENT);

    User user = authenticationService.getAuthenticatedUser();

    TCCSubject newTCCSubject = tccSubjectService.createTCCSubject(tccSubjectDTO, user);

    return new ResponseEntity<>(new TCCSubjectResponseDTO(newTCCSubject), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<TCCSubjectResponseDTO>> listTCCSubjects(
    @RequestParam(name = "createdBy", required = false) Long createdById
  ) {
    authenticationService.ensureUserTypes(User.Type.PROFESSOR, User.Type.STUDENT);

    User user = authenticationService.getAuthenticatedUser();

    Set<TCCSubject> tccSubjects;
    if (createdById == null) {
      tccSubjects = tccSubjectService.listTCCSubjectsVisibleToUser(user);
    } else {
      tccSubjects = tccSubjectService.listTCCSubjectsCreatedByUser(createdById);
    }

    return new ResponseEntity<>(TCCSubjectResponseDTO.fromTCCSubjects(tccSubjects), HttpStatus.OK);
  }
}
