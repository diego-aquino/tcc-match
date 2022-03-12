package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import java.util.Set;

public class ListTCCGuidanceRequestsResponseDTO {

  private Set<TCCGuidanceRequest> tccGuidanceRequests;

  public ListTCCGuidanceRequestsResponseDTO(Set<TCCGuidanceRequest> tccGuidanceRequests) {
    this.tccGuidanceRequests = tccGuidanceRequests;
  }

  public Set<TCCGuidanceRequest> getTccGuidanceRequests() {
    return tccGuidanceRequests;
  }
}
