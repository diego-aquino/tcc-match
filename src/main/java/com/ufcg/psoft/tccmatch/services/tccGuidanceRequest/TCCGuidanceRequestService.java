package com.ufcg.psoft.tccmatch.services.tccGuidanceRequest;

import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccGuidanceRequests.TCCGuidanceRequestRepository;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class TCCGuidanceRequestService {

  @Autowired
  TCCGuidanceRequestRepository tccGuidanceRequestRepository;

  @Autowired
  TCCSubjectService tccSubjectService;

  public Set<TCCGuidanceRequest> listTCCGuidanceRequests(User user) {
    // If User is Student
    // if User is Professor

    return (Set<TCCGuidanceRequest>) tccGuidanceRequestRepository.findAll();
  }

  public TCCGuidanceRequest createTCCGuidanceRequest(
    CreateTCCGuidanceRequestRequestDTO createTccGuidanceRequestDTO,
    User user
  ) {
    User userCreatedSubject = tccSubjectService.getCreatedBySubjectId(
      createTccGuidanceRequestDTO.getTccSubjectId()
    );

    TCCGuidanceRequest tccGuidanceRequest = new TCCGuidanceRequest(
      createTccGuidanceRequestDTO.getMessage(),
      userCreatedSubject,
      user
    );

    tccGuidanceRequestRepository.save(tccGuidanceRequest);

    return tccGuidanceRequest;
  }
}
