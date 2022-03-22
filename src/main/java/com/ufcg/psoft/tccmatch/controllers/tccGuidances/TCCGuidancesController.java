package com.ufcg.psoft.tccmatch.controllers.tccGuidances;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidances.TCCGuidanceResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccGuidances.TCCGuidanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tcc-guidances")
public class TCCGuidancesController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private TCCGuidanceService tccGuidanceService;

  @PostMapping
  public ResponseEntity<TCCGuidanceResponseDTO> createTCCGuidance(
    @RequestBody CreateTCCGuidanceDTO tccGuidanceDTO
  ) {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    TCCGuidance tccGuidance = tccGuidanceService.createTCCGuidance(tccGuidanceDTO);
    return new ResponseEntity<>(new TCCGuidanceResponseDTO(tccGuidance), HttpStatus.CREATED);
  }
}
