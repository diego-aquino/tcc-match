package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestResponseDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.ListTCCGuidanceRequestsResponseDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectResponseDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.ListTCCSubjectResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tcc-guidance-requests/")
@CrossOrigin
public class TCCGuidanceRequestsController {

  @Autowired
  TCCGuidanceRequestService tccGuidanceRequestService;

  @Autowired
  AuthenticationService authenticationService;

  @PostMapping("/{tccSubjectId}")
  public ResponseEntity<CreateTCCGuidanceRequestResponseDTO> createTCCSubject(
    @RequestBody CreateTCCGuidanceRequestRequestDTO createTccGuidanceRequestDTO
  ) {
    User user = authenticationService.getAuthenticatedUser(); //Has to be Student

    TCCGuidanceRequest newTCCGuidanceRequest = tccGuidanceRequestService.createTCCGuidanceRequest(
      createTccGuidanceRequestDTO,
      user
    );

    return new ResponseEntity<CreateTCCGuidanceRequestResponseDTO>(
      new CreateTCCGuidanceRequestResponseDTO(newTCCGuidanceRequest),
      HttpStatus.CREATED
    );
  }

  @GetMapping("/")
  public ResponseEntity<ListTCCGuidanceRequestsResponseDTO> listTCCGuidanceRequests() {
    User user = authenticationService.getAuthenticatedUser(); //Has to be professor

    Set<TCCGuidanceRequest> TCCGuidanceRequests = tccGuidanceRequestService.listTCCGuidanceRequests(
      user
    );

    return new ResponseEntity<ListTCCGuidanceRequestsResponseDTO>(
      new ListTCCGuidanceRequestsResponseDTO(TCCGuidanceRequests),
      HttpStatus.OK
    );
  }
}
