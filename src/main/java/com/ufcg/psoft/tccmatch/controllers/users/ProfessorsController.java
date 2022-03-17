package com.ufcg.psoft.tccmatch.controllers.users;

import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.dto.users.ProfessorResponseDTO;
import com.ufcg.psoft.tccmatch.dto.users.UpdateProfessorDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.ForbiddenUserUpdateException;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/professors")
public class ProfessorsController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private ProfessorService professorService;

  @PostMapping
  public ResponseEntity<ProfessorResponseDTO> createProfessor(
    @RequestBody CreateProfessorDTO createProfessorDTO
  ) {
    authenticationService.ensureUserTypes(User.Type.COORDINATOR);

    Professor professor = professorService.createProfessor(createProfessorDTO);
    return new ResponseEntity<>(new ProfessorResponseDTO(professor), HttpStatus.CREATED);
  }

  @PatchMapping("{professorId}")
  public ResponseEntity<ProfessorResponseDTO> updateStudent(
    @PathVariable("professorId") Long professorId,
    @RequestBody UpdateProfessorDTO updateProfessorDTO
  ) {
    User authenticatedUser = authenticationService.getAuthenticatedUser();
    boolean hasPermission = professorService.hasPermissionToUpdateProfessor(
      authenticatedUser,
      professorId
    );

    if (!hasPermission) {
      throw new ForbiddenUserUpdateException();
    }

    Professor updatedProfessor = professorService.updateProfessor(professorId, updateProfessorDTO);
    return new ResponseEntity<>(new ProfessorResponseDTO(updatedProfessor), HttpStatus.OK);
  }
}
