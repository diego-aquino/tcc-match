package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.ReviewTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestNotFound;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestNotPending;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestUnauthorizedProfessor;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewTCCGuidanceRequestTests extends IntegrationTests {

  @Autowired
  private TCCGuidanceRequestService tccGuidanceRequestService;

  @Autowired
  private ProfessorService professorService;

  private String otherProfessorEmail = "professor2@email.com";
  private String otherProfessorRawPassword = "12345678";
  private String otherProfessorName = "Professor Elmar";

  private Professor professor;
  private Student student;
  private TCCSubject tccSubject;
  private TCCGuidanceRequest tccGuidanceRequest;

  @BeforeEach
  void beforeEach() {
    professor = createMockProfessor();
    student = createMockStudent();
    tccSubject = createMockTCCSubject(professor);
    tccGuidanceRequest =
      createMockTCCGuidanceRequest(tccSubject.getId(), professor.getId(), student);
  }

  @Test
  void validApprovedReviewTCCGuidanceRequest() throws Exception {
    String professorToken = loginWithMockProfessor();

    assertEquals(TCCGuidanceRequest.Status.PENDING, tccGuidanceRequest.getStatus());
    assertEquals("", tccGuidanceRequest.getMessage());

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      true,
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      tccGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status", is("APPROVED")))
      .andExpect(jsonPath("$.message", is("Nice Job! Let's work together to not end the world!")))
      .andExpect(jsonPath("$.createdBy", is(student.getId().intValue())))
      .andExpect(jsonPath("$.requestedTo", is(professor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubject", is(tccSubject.getId().intValue())));

    tccGuidanceRequest = tccGuidanceRequestService.findById(tccGuidanceRequest.getId()).get();

    assertEquals(TCCGuidanceRequest.Status.APPROVED, tccGuidanceRequest.getStatus());
    assertEquals(
      "Nice Job! Let's work together to not end the world!",
      tccGuidanceRequest.getMessage()
    );
  }

  @Test
  void validRefusedReviewTCCGuidanceRequest() throws Exception {
    String professorToken = loginWithMockProfessor();

    assertEquals(TCCGuidanceRequest.Status.PENDING, tccGuidanceRequest.getStatus());
    assertEquals("", tccGuidanceRequest.getMessage());

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      false,
      "Bollocks! A.I is completely safe, what were you thinking?!"
    );

    makeReviewTCCGuidanceRequestRequest(
      tccGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isOk());

    tccGuidanceRequest = tccGuidanceRequestService.findById(tccGuidanceRequest.getId()).get();

    assertEquals(TCCGuidanceRequest.Status.DENIED, tccGuidanceRequest.getStatus());
    assertEquals(
      "Bollocks! A.I is completely safe, what were you thinking?!",
      tccGuidanceRequest.getMessage()
    );
  }

  @Test
  void reviewInexistentTCCGuidanceRequest() throws Exception {
    String professorToken = loginWithMockProfessor();

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      false,
      "Bollocks! A.I is completely safe, what were you thinking?!"
    );

    makeReviewTCCGuidanceRequestRequest(404, reviewTCCGuidanceRequestRequestDTO, professorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(TCCGuidanceRequestNotFound.message())));
  }

  @Test
  void reviewTCCGuidanceRequestTwice() throws Exception {
    String professorToken = loginWithMockProfessor();

    assertEquals(TCCGuidanceRequest.Status.PENDING, tccGuidanceRequest.getStatus());
    assertEquals("", tccGuidanceRequest.getMessage());

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      true,
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      tccGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isOk());

    tccGuidanceRequest = tccGuidanceRequestService.findById(tccGuidanceRequest.getId()).get();

    assertEquals(TCCGuidanceRequest.Status.APPROVED, tccGuidanceRequest.getStatus());
    assertEquals(
      "Nice Job! Let's work together to not end the world!",
      tccGuidanceRequest.getMessage()
    );

    makeReviewTCCGuidanceRequestRequest(
      tccGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(TCCGuidanceRequestNotPending.message())));
  }

  @Test
  void reviewTCCGuidanceRequestWrongProfessor() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      otherProfessorEmail,
      otherProfessorRawPassword,
      otherProfessorName,
      professorLaboratories
    );

    professorService.createProfessor(createProfessorDTO);
    String otherProfessorToken = login(otherProfessorEmail, otherProfessorRawPassword);

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      true,
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      tccGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      otherProfessorToken
    )
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(TCCGuidanceRequestUnauthorizedProfessor.message())));
  }

  private ResultActions makeReviewTCCGuidanceRequestRequest(
    long tccGuidanceRequestId,
    ReviewTCCGuidanceRequestRequestDTO reviewTccGuidanceRequestDTO,
    String userToken
  ) throws Exception {
    return mvc.perform(
      authenticated(post("/api/tcc-guidance-requests/review/" + tccGuidanceRequestId), userToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(reviewTccGuidanceRequestDTO))
    );
  }
}
