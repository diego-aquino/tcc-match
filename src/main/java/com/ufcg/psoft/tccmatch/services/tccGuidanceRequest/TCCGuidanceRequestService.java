package com.ufcg.psoft.tccmatch.services.tccGuidanceRequest;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.ReviewTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestNotFound;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestNotPending;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidanceRequests.TCCGuidanceRequestUnauthorizedProfessor;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccGuidanceRequests.TCCGuidanceRequestRepository;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import com.ufcg.psoft.tccmatch.services.users.UserService;
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
  UserService<Professor> userService;

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
    TCCSubject tccSubject = tccSubjectService
      .findTCCSubjectById(createTccGuidanceRequestDTO.getTccSubjectId())
      .get();

    User userCreatedSubject = tccSubjectService.getCreatedBySubjectId(
      createTccGuidanceRequestDTO.getTccSubjectId()
    );
    TCCGuidanceRequest tccGuidanceRequest;

    if (user.getType() == User.Type.PROFESSOR) {
      tccGuidanceRequest = new TCCGuidanceRequest(user, (Professor) userCreatedSubject, tccSubject);
    } else {
      //Add error checking if the passed id is invalid or not a Professor Id

      Professor professor = (Professor) userService
        .findUserById(createTccGuidanceRequestDTO.getProfessorId())
        .get();

      tccGuidanceRequest = new TCCGuidanceRequest(user, professor, tccSubject);
    }

    tccGuidanceRequestRepository.save(tccGuidanceRequest);

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
    if (
      tccGuidanceRequest.getStatus() != TCCGuidanceRequest.Status.PENDING
    ) throw new TCCGuidanceRequestNotPending();
    if (
      !tccGuidanceRequest.getRequestedTo().equals(professor)
    ) throw new TCCGuidanceRequestUnauthorizedProfessor();

    tccGuidanceRequest.setMessage(reviewTccGuidanceRequestDTO.getMessage());
    tccGuidanceRequest.setStatus(
      reviewTccGuidanceRequestDTO.getIsApproved()
        ? TCCGuidanceRequest.Status.APPROVED
        : TCCGuidanceRequest.Status.DENIED
    );

    tccGuidanceRequestRepository.save(tccGuidanceRequest);

    return tccGuidanceRequest;
  }
}
