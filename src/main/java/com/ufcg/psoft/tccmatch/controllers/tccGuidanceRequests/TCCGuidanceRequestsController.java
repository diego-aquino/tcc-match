package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.ReviewTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.TCCGuidanceRequestResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tcc-guidance-requests")
@CrossOrigin
public class TCCGuidanceRequestsController {

  @Autowired
  TCCGuidanceRequestService tccGuidanceRequestService;

  @Autowired
  AuthenticationService authenticationService;

  @PostMapping
  public ResponseEntity<TCCGuidanceRequestResponseDTO> createTCCGuidanceRequest(
    @RequestBody CreateTCCGuidanceRequestRequestDTO createTccGuidanceRequestDTO
  ) {
    authenticationService.ensureUserTypes(User.Type.STUDENT);

    User student = authenticationService.getAuthenticatedUser();

    TCCGuidanceRequest newTCCGuidanceRequest = tccGuidanceRequestService.createTCCGuidanceRequest(
      createTccGuidanceRequestDTO,
      (Student) student
    );

    return new ResponseEntity<>(
      new TCCGuidanceRequestResponseDTO(newTCCGuidanceRequest),
      HttpStatus.CREATED
    );
  }

  @PostMapping("/review/{tccGuidanceRequestId}")
  public ResponseEntity<TCCGuidanceRequestResponseDTO> reviewTCCGuidanceRequest(
    @PathVariable long tccGuidanceRequestId,
    @RequestBody ReviewTCCGuidanceRequestRequestDTO reviewTccGuidanceRequestDTO
  ) {
    authenticationService.ensureUserTypes(User.Type.PROFESSOR);

    User user = authenticationService.getAuthenticatedUser();

    TCCGuidanceRequest updatedTCCGuidanceRequest = tccGuidanceRequestService.reviewTCCGuidanceRequest(
      tccGuidanceRequestId,
      reviewTccGuidanceRequestDTO,
      (Professor) user
    );

    return new ResponseEntity<>(
      new TCCGuidanceRequestResponseDTO(updatedTCCGuidanceRequest),
      HttpStatus.OK
    );
  }

  @GetMapping
  public ResponseEntity<List<TCCGuidanceRequestResponseDTO>> listTCCGuidanceRequests() {
    authenticationService.ensureUserTypes(User.Type.PROFESSOR);

    User user = authenticationService.getAuthenticatedUser();

    Set<TCCGuidanceRequest> tccGuidanceRequests = tccGuidanceRequestService.listTCCGuidanceRequests(
      (Professor) user
    );

    return new ResponseEntity<>(
      TCCGuidanceRequestResponseDTO.fromTCCGuidanceRequests(tccGuidanceRequests),
      HttpStatus.OK
    );
  }
}
