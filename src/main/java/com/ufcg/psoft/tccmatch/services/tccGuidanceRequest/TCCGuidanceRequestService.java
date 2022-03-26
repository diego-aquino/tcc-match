package com.ufcg.psoft.tccmatch.services.tccGuidanceRequest;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.ReviewTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.EmptyTCCGuidanceRequestReviewMessageException;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestNotFound;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestNotPending;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestUnauthorizedProfessor;
import com.ufcg.psoft.tccmatch.exceptions.tccSubjects.TCCSubjectNotFoundException;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccGuidanceRequests.TCCGuidanceRequestRepository;
import com.ufcg.psoft.tccmatch.services.notifications.NotificationService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCGuidanceRequestService {

  @Autowired
  TCCGuidanceRequestRepository tccGuidanceRequestRepository;

  @Autowired
  TCCSubjectService tccSubjectService;

  @Autowired
  ProfessorService professorService;

  @Autowired
  NotificationService notificationService;

  public Optional<TCCGuidanceRequest> findById(Long id) {
    return tccGuidanceRequestRepository.findById(id);
  }

  public Set<TCCGuidanceRequest> listTCCGuidanceRequests(Professor user) {
    return tccGuidanceRequestRepository.findByRequestedTo(user);
  }

  public TCCGuidanceRequest createTCCGuidanceRequest(
    CreateTCCGuidanceRequestRequestDTO createTccGuidanceRequestDTO,
    Student user
  ) {
    Optional<TCCSubject> optionalTCCSubject = tccSubjectService.findTCCSubjectById(
      createTccGuidanceRequestDTO.getTccSubjectId()
    );

    if (optionalTCCSubject.isEmpty()) {
      throw new TCCSubjectNotFoundException();
    }

    TCCSubject tccSubject = optionalTCCSubject.get();
    User creator = tccSubject.getCreatedBy();

    TCCGuidanceRequest tccGuidanceRequest;
    if (user.getType() == User.Type.PROFESSOR) {
      tccGuidanceRequest = new TCCGuidanceRequest(user, (Professor) creator, tccSubject);
    } else {
      Professor professor = professorService.findByIdOrThrow(
        createTccGuidanceRequestDTO.getProfessorId()
      );
      tccGuidanceRequest = new TCCGuidanceRequest(user, professor, tccSubject);
    }

    tccGuidanceRequestRepository.save(tccGuidanceRequest);
    notificationService.handleTCCGuidanceRequestCreated(tccGuidanceRequest, creator);

    return tccGuidanceRequest;
  }

  public TCCGuidanceRequest reviewTCCGuidanceRequest(
    long tccGuidanceRequestId,
    ReviewTCCGuidanceRequestRequestDTO reviewTccGuidanceRequestDTO,
    Professor professor
  ) {
    Optional<TCCGuidanceRequest> tccGuidanceRequestOp = findById(tccGuidanceRequestId);
    if (tccGuidanceRequestOp.isEmpty()) throw new TCCGuidanceRequestNotFound();

    TCCGuidanceRequest tccGuidanceRequest = tccGuidanceRequestOp.get();

    if (tccGuidanceRequest.getStatus() != TCCGuidanceRequest.Status.PENDING) {
      throw new TCCGuidanceRequestNotPending();
    }
    if (!tccGuidanceRequest.getRequestedTo().equals(professor)) {
      throw new TCCGuidanceRequestUnauthorizedProfessor();
    }

    String reviewMessage = reviewTccGuidanceRequestDTO.getMessage();
    if (reviewMessage == null || reviewMessage.isBlank()) {
      throw new EmptyTCCGuidanceRequestReviewMessageException();
    }

    tccGuidanceRequest.setMessage(reviewMessage);
    tccGuidanceRequest.setStatus(
      reviewTccGuidanceRequestDTO.getIsApproved()
        ? TCCGuidanceRequest.Status.APPROVED
        : TCCGuidanceRequest.Status.DENIED
    );

    tccGuidanceRequestRepository.save(tccGuidanceRequest);
    if (reviewTccGuidanceRequestDTO.getIsApproved()) {
      notificationService.handleTCCGuidanceRequestAccepted(tccGuidanceRequest);
    }

    return tccGuidanceRequest;
  }
}
