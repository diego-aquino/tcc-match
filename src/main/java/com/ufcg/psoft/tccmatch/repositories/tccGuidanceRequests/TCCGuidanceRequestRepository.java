package com.ufcg.psoft.tccmatch.repositories.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCGuidanceRequestRepository extends JpaRepository<TCCGuidanceRequest, Long> {
  Set<TCCGuidanceRequest> findByRequestedTo(Professor requestedToId);
}
