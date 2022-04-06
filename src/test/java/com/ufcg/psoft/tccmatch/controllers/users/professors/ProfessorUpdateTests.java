package com.ufcg.psoft.tccmatch.controllers.users.professors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.dto.users.UpdateProfessorDTO;
import com.ufcg.psoft.tccmatch.exceptions.users.EmailAlreadyInUseException;
import com.ufcg.psoft.tccmatch.exceptions.users.EmptyUserNameException;
import com.ufcg.psoft.tccmatch.exceptions.users.ForbiddenUserUpdateException;
import com.ufcg.psoft.tccmatch.exceptions.users.InvalidEmailApiException;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.services.users.professors.InvalidGuidanceQuotaException;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class ProfessorUpdateTests extends ProfessorTests {

  @Autowired
  private ProfessorService professorService;

  private Professor professor;

  private String professorToken;

  @BeforeEach
  void beforeEach() {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );

    professor = professorService.createProfessor(createProfessorDTO);
    professorToken = login(professorEmail, professorRawPassword);
  }

  @Test
  void validNameUpdate() throws Exception {
    String newName = "New Professor";

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.empty(),
      Optional.of(newName),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.email", is(professor.getEmail())))
      .andExpect(jsonPath("$.name", is(newName)))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(professor.getLaboratories()))))
      .andExpect(jsonPath("$.guidanceQuota", is(professor.getGuidanceQuota())));
  }

  @Test
  void validEmailUpdate() throws Exception {
    String newEmail = "newprofessor@email.com";

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.of(newEmail),
      Optional.empty(),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.email", is(newEmail)))
      .andExpect(jsonPath("$.name", is(professor.getName())))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(professor.getLaboratories()))))
      .andExpect(jsonPath("$.guidanceQuota", is(professor.getGuidanceQuota())));
  }

  @Test
  void validLaboratoriesUpdate() throws Exception {
    Set<String> newLaboratories = new HashSet<>(List.of("A"));

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.empty(),
      Optional.empty(),
      Optional.of(newLaboratories),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.email", is(professor.getEmail())))
      .andExpect(jsonPath("$.name", is(professor.getName())))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(newLaboratories))))
      .andExpect(jsonPath("$.guidanceQuota", is(professor.getGuidanceQuota())));
  }

  @Test
  void validGuidanceQuotaUpdate() throws Exception {
    int newGuidanceQuota = 3;

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      Optional.of(newGuidanceQuota)
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.email", is(professor.getEmail())))
      .andExpect(jsonPath("$.name", is(professor.getName())))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(professor.getLaboratories()))))
      .andExpect(jsonPath("$.guidanceQuota", is(newGuidanceQuota)));
  }

  @Test
  void validUpdateByCoordinator() throws Exception {
    String newEmail = "newprofessor@email.com";

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.of(newEmail),
      Optional.empty(),
      Optional.empty(),
      Optional.empty()
    );

    String coordinatorToken = loginWithDefaultCoordinator();

    makeUpdateProfessorRequest(professor.getId(), coordinatorToken, updateProfessorDTO)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.email", is(newEmail)))
      .andExpect(jsonPath("$.name", is(professor.getName())))
      .andExpect(jsonPath("$.laboratories", is(List.copyOf(professor.getLaboratories()))))
      .andExpect(jsonPath("$.guidanceQuota", is(professor.getGuidanceQuota())));
  }

  @Test
  void errorOnEmailAlreadyInUse() throws Exception {
    String existingProfessorEmail = "existingprofessor@email.com";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      existingProfessorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );
    professorService.createProfessor(createProfessorDTO);

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.of(existingProfessorEmail),
      Optional.empty(),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message", is(EmailAlreadyInUseException.message())));
  }

  @Test
  void errorOnInvalidEmail() throws Exception {
    String invalidEmail = "email.com";

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.of(invalidEmail),
      Optional.empty(),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidEmailApiException.message())));
  }

  @Test
  void errorOnEmptyName() throws Exception {
    String emptyName = "";

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.empty(),
      Optional.of(emptyName),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(EmptyUserNameException.message())));
  }

  @Test
  void errorOnInvalidGuidanceQuota() throws Exception {
    int invalidGuidanceQuota = -1;

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      Optional.of(invalidGuidanceQuota)
    );

    makeUpdateProfessorRequest(professor.getId(), professorToken, updateProfessorDTO)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(InvalidGuidanceQuotaException.message())));
  }

  @Test
  void errorOnMissingPermissionToUpdate() throws Exception {
    String anotherProfessorEmail = "anotherprofessor@email.com";

    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      anotherProfessorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );

    professorService.createProfessor(createProfessorDTO);
    String anotherProfessorToken = login(anotherProfessorEmail, professorRawPassword);

    String newName = "New Professor";

    UpdateProfessorDTO updateProfessorDTO = new UpdateProfessorDTO(
      Optional.empty(),
      Optional.of(newName),
      Optional.empty(),
      Optional.empty()
    );

    makeUpdateProfessorRequest(professor.getId(), anotherProfessorToken, updateProfessorDTO)
      .andExpect(status().isForbidden())
      .andExpect(jsonPath("$.message", is(ForbiddenUserUpdateException.message())));
  }

  private ResultActions makeUpdateProfessorRequest(
    Long professorId,
    String token,
    UpdateProfessorDTO updateProfessorDTO
  ) throws Exception {
    String endpoint = String.format("/api/users/professors/%d", professorId);

    return mvc.perform(
      authenticated(patch(endpoint), token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(updateProfessorDTO))
    );
  }
}
