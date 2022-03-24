package com.ufcg.psoft.tccmatch.controllers.tccGuidances;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidances.TCCGuidanceReportDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidances.TCCGuidanceResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccGuidances.TCCGuidanceService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      @RequestBody CreateTCCGuidanceDTO tccGuidanceDTO) {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    TCCGuidance tccGuidance = tccGuidanceService.createTCCGuidance(tccGuidanceDTO);
    return new ResponseEntity<>(new TCCGuidanceResponseDTO(tccGuidance), HttpStatus.CREATED);
  }

  @PostMapping("/finish/{tccGuidanceId}")
  public ResponseEntity<TCCGuidanceResponseDTO> finishTCCGuidance(
      @PathVariable("tccGuidanceId") Long tccGuidanceId) {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    TCCGuidance finishedTCCGuidance = tccGuidanceService.finishTCCGuidance(tccGuidanceId);
    return new ResponseEntity<>(new TCCGuidanceResponseDTO(finishedTCCGuidance), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<TCCGuidanceResponseDTO>> listTCCGuidances(
      @RequestParam("period") Optional<String> period,
      @RequestParam("finished") Optional<Boolean> isFinished) {
    authenticationService.ensureUserTypes(
        User.Type.STUDENT,
        User.Type.PROFESSOR,
        User.Type.COORDINATOR);

    List<TCCGuidance> tccGuidances = tccGuidanceService.listTCCGuidances(period, isFinished);

    return new ResponseEntity<>(
        TCCGuidanceResponseDTO.fromTCCGuidances(tccGuidances),
        HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<TCCGuidanceReportDTO>> reportTCCGuidances(
    @RequestParam("period") Optional<String> period,
    @RequestParam("finished") Optional<Boolean> isFinished
  ){
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);
    List<TCCGuidance> tccGuidances = tccGuidanceService.listTCCGuidances(period, isFinished);
    return new ResponseEntity<>(
      TCCGuidanceReportDTO.fromTCCGuidances(tccGuidances),
      HttpStatus.OK
    );
  }

}
