package com.ufcg.psoft.tccmatch.controllers.fieldsOfStudy;

import java.util.List;
import java.util.Optional;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.dto.fieldsOfStudy.FieldOfStudyResponseDTO;
import com.ufcg.psoft.tccmatch.dto.users.ProfessorResponseDTO;
import com.ufcg.psoft.tccmatch.exceptions.fieldsOfStudy.FieldNotFoundException;
import com.ufcg.psoft.tccmatch.services.fieldsOfStudy.FieldsOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PostMapping("/select/{fieldOfStudyId}")
  public ResponseEntity<FieldOfStudyResponseDTO> selectFieldOfStudy(
    @PathVariable("fieldOfStudyId") Long idField
  ) {
    authenticationService.ensureUserTypes(User.Type.STUDENT, User.Type.PROFESSOR);
    User authenticatedUser = authenticationService.getAuthenticatedUser();
    Optional<FieldOfStudy> field = fieldsOfStudyService.findById(idField);

    if (field.isEmpty()) {
      throw new FieldNotFoundException();
    }

    FieldOfStudy fieldOfStudy = field.get();
    fieldsOfStudyService.selectFieldOfStudy(authenticatedUser, fieldOfStudy);
    return new ResponseEntity<>(new FieldOfStudyResponseDTO(fieldOfStudy), HttpStatus.OK);
  }
  @GetMapping("/professors") 
  public ResponseEntity<List<ProfessorResponseDTO>> listProfessors(){
    authenticationService.ensureUserTypes(User.Type.STUDENT);
    Student authenticatedUser = (Student) authenticationService.getAuthenticatedUser();

    List<Professor> listProfessors = fieldsOfStudyService.getProfessors(authenticatedUser);
    return new ResponseEntity<>(ProfessorResponseDTO.listProfessors(listProfessors), HttpStatus.OK);
  }
}
