package com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests;

public class CreateTCCGuidanceRequestRequestDTO {

  private long tccSubjectId;

  private long professorId;

  public CreateTCCGuidanceRequestRequestDTO(long tccSubjectId, long professorId) {
    this.tccSubjectId = tccSubjectId;
    this.professorId = professorId;
  }

  public long getTccSubjectId() {
    return tccSubjectId;
  }

  public long getProfessorId() {
    return professorId;
  }
}
