package com.ufcg.psoft.tccmatch.controllers.tccGuidanceRequests;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.HashSet;
import java.util.Set;
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

  @BeforeEach
  void beforeEach() {
    createMockProfessor();
    createMockStudent();
    createMockTCCSubject(mockProfessor);
    createMockTCCGuidanceRequest(mockTCCSubject.getId(), mockProfessor.getId(), mockStudent);
  }

  @Test
  void ValidApprovedReviewTCCGuidanceRequest() throws Exception {
    String professorToken = loginProgrammaticallyWithMockProfessor();

    assertEquals(mockTCCGuidanceRequest.getStatus(), TCCGuidanceRequest.Status.PENDING);
    assertEquals(mockTCCGuidanceRequest.getMessage(), "");

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      true,
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      mockTCCGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status", is("APPROVED")))
      .andExpect(jsonPath("$.message", is("Nice Job! Let's work together to not end the world!")))
      .andExpect(jsonPath("$.createdBy", is(mockStudent.getId().intValue())))
      .andExpect(jsonPath("$.requestedTo", is(mockProfessor.getId().intValue())))
      .andExpect(jsonPath("$.tccSubject", is(mockTCCSubject.getId().intValue())));

    mockTCCGuidanceRequest =
      tccGuidanceRequestService.findById(mockTCCGuidanceRequest.getId()).get();

    assertEquals(TCCGuidanceRequest.Status.APPROVED, mockTCCGuidanceRequest.getStatus());
    assertEquals(
      "Nice Job! Let's work together to not end the world!",
      mockTCCGuidanceRequest.getMessage()
    );
  }

  @Test
  void ValidRefusedReviewTCCGuidanceRequest() throws Exception {
    String professorToken = loginProgrammaticallyWithMockProfessor();

    assertEquals(mockTCCGuidanceRequest.getStatus(), TCCGuidanceRequest.Status.PENDING);
    assertEquals(mockTCCGuidanceRequest.getMessage(), "");

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      false,
      "Bollocks! A.I is completely safe, what were you thinking?!"
    );

    makeReviewTCCGuidanceRequestRequest(
      mockTCCGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isOk());

    mockTCCGuidanceRequest =
      tccGuidanceRequestService.findById(mockTCCGuidanceRequest.getId()).get();

    assertEquals(mockTCCGuidanceRequest.getStatus(), TCCGuidanceRequest.Status.DENIED);
    assertEquals(
      mockTCCGuidanceRequest.getMessage(),
      "Bollocks! A.I is completely safe, what were you thinking?!"
    );
  }

  @Test
  void ReviewInexistentTCCGuidanceRequest() throws Exception {
    String professorToken = loginProgrammaticallyWithMockProfessor();

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      false,
      "Bollocks! A.I is completely safe, what were you thinking?!"
    );

    makeReviewTCCGuidanceRequestRequest(404, reviewTCCGuidanceRequestRequestDTO, professorToken)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(TCCGuidanceRequestNotFound.message())));
  }

  @Test
  void ReviewTCCGuidanceRequestTwice() throws Exception {
    String professorToken = loginProgrammaticallyWithMockProfessor();

    assertEquals(mockTCCGuidanceRequest.getStatus(), TCCGuidanceRequest.Status.PENDING);
    assertEquals(mockTCCGuidanceRequest.getMessage(), "");

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      true,
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      mockTCCGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isOk());

    mockTCCGuidanceRequest =
      tccGuidanceRequestService.findById(mockTCCGuidanceRequest.getId()).get();

    assertEquals(mockTCCGuidanceRequest.getStatus(), TCCGuidanceRequest.Status.APPROVED);
    assertEquals(
      mockTCCGuidanceRequest.getMessage(),
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      mockTCCGuidanceRequest.getId(),
      reviewTCCGuidanceRequestRequestDTO,
      professorToken
    )
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message", is(TCCGuidanceRequestNotPending.message())));
  }

  @Test
  void ReviewTCCGuidanceRequestWrongProfessor() throws Exception {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      otherProfessorEmail,
      otherProfessorRawPassword,
      otherProfessorName,
      professorLaboratories
    );

    professorService.createProfessor(createProfessorDTO);
    String otherProfessorToken = loginProgrammatically(
      otherProfessorEmail,
      otherProfessorRawPassword
    );

    ReviewTCCGuidanceRequestRequestDTO reviewTCCGuidanceRequestRequestDTO = new ReviewTCCGuidanceRequestRequestDTO(
      true,
      "Nice Job! Let's work together to not end the world!"
    );

    makeReviewTCCGuidanceRequestRequest(
      mockTCCGuidanceRequest.getId(),
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
