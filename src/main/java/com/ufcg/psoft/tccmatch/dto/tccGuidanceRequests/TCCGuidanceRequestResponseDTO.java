package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest.Status;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TCCGuidanceRequestResponseDTO {

  private Long id;
  private Status status;
  private String message;
  private Long createdBy;
  private Long tccSubject;
  private Long requestedTo;

  public TCCGuidanceRequestResponseDTO(TCCGuidanceRequest tccGuidanceRequest) {
    this.id = tccGuidanceRequest.getId();
    this.status = tccGuidanceRequest.getStatus();
    this.message = tccGuidanceRequest.getMessage();
    this.createdBy = tccGuidanceRequest.getCreatedBy().getId();
    this.requestedTo = tccGuidanceRequest.getRequestedTo().getId();
    this.tccSubject = tccGuidanceRequest.getTccSubject().getId();
  }

  public Long getId() {
    return id;
  }

  public Long getTccSubject() {
    return tccSubject;
  }

  public Status getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public Long getRequestedTo() {
    return requestedTo;
  }

  public static List<TCCGuidanceRequestResponseDTO> fromTCCGuidanceRequests(
    Set<TCCGuidanceRequest> tccGuidanceRequests
  ) {
    return Arrays.asList(
      tccGuidanceRequests
        .stream()
        .map(TCCGuidanceRequestResponseDTO::new)
        .toArray(TCCGuidanceRequestResponseDTO[]::new)
    );
  }
}
