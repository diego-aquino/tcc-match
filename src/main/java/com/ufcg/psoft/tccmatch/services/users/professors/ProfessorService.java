package com.ufcg.psoft.tccmatch.services.users.professors;

import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.dto.users.UpdateProfessorDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.ProfessorNotFoundException;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.users.UserRepository;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

  @Autowired
  private UserService<User> userService;

  @Autowired
  private UserRepository<Professor> userRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private ProfessorValidator professorValidator;

  public Professor createProfessor(CreateProfessorDTO createProfessorDTO) {
    String email = professorValidator.validateEmail(createProfessorDTO.getEmail());
    String rawPassword = professorValidator.validatePassword((createProfessorDTO.getPassword()));
    String name = professorValidator.validateName(createProfessorDTO.getName());
    Set<String> laboratories = professorValidator.validateLaboratories(
        createProfessorDTO.getLaboratories());

    userService.ensureEmailIsNotInUse(email);

    String encodedPassword = authenticationService.encodePassword(rawPassword);

    Professor professor = new Professor(email, encodedPassword, name, laboratories);
    userRepository.save(professor);

    return professor;
  }

  public Professor updateProfessor(Long professorId, UpdateProfessorDTO updateProfessorDTO) {
    Professor professor = findByIdOrThrow(professorId);

    userService.updateEmailIfProvided(updateProfessorDTO.getEmail(), professor);
    userService.updateNameIfProvided(updateProfessorDTO.getName(), professor);
    updateLaboratoriesIfProvided(updateProfessorDTO.getLaboratories(), professor);
    updateGuidanceQuotaIfProvided(updateProfessorDTO.getGuidanceQuota(), professor);
    userRepository.save(professor);

    return professor;
  }

  private void updateLaboratoriesIfProvided(
      Optional<Set<String>> optionalLaboratories,
      Professor professor) {
    if (optionalLaboratories.isEmpty())
      return;

    Set<String> newLaboratories = professorValidator.validateLaboratories(
        optionalLaboratories.get());
    professor.setLaboratories(newLaboratories);
  }

  private void updateGuidanceQuotaIfProvided(
      Optional<Integer> optionalGuidanceQuota,
      Professor professor) {
    if (optionalGuidanceQuota.isEmpty())
      return;

    int newGuidanceQuota = professorValidator.validateGuidanceQuota(optionalGuidanceQuota.get());
    professor.setGuidanceQuota(newGuidanceQuota);
  }

  public Professor removeProfessor(Long professorId) {
    Professor professor = findByIdOrThrow(professorId);
    userRepository.delete(professor);
    return professor;
  }

  public boolean hasPermissionToUpdateProfessor(User authenticatedUser, Long professorId) {
    if (authenticatedUser == null)
      return false;
    if (authenticatedUser.getType() == User.Type.COORDINATOR)
      return true;

    return (authenticatedUser.getType() == User.Type.PROFESSOR &&
        authenticatedUser.getId().equals(professorId));
  }

  public void selectFieldOfStudy(Professor professor, FieldOfStudy fieldOfStudy) {
    professor.addField(fieldOfStudy);
    userRepository.save(professor);
  }

  public Professor findByIdOrThrow(Long id) {
    Optional<User> optionalProfessor = userService.findUserById(id);

    boolean professorWasFound = optionalProfessor.isPresent()
        && optionalProfessor.get().getType() == User.Type.PROFESSOR;
    if (!professorWasFound)
      throw new ProfessorNotFoundException();

    return (Professor) optionalProfessor.get();
  }
}
