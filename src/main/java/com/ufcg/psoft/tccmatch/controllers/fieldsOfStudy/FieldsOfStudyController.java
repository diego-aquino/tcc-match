package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import com.ufcg.psoft.tccmatch.dto.fieldsOfStudy.FieldOfStudyResponseDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fields-of-study")
public class FieldsOfStudyController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private FieldsOfStudyService fieldsOfStudyService;

  @PostMapping
  public ResponseEntity<FieldOfStudyResponseDTO> createFieldsOfStudy(@RequestBody String name) {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    FieldOfStudy fieldOfStudy = fieldsOfStudyService.createFieldsOfStudy(name);
    return new ResponseEntity<>(new FieldOfStudyResponseDTO(fieldOfStudy), HttpStatus.CREATED);
  }
}
