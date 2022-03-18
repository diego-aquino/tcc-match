package com.ufcg.psoft.tccmatch.dto.tccGuidances;

import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;

public class TCCGuidanceResponseDTO {

  private Long id;
  private Long studentId;
  private Long professorId;
  private String period;
  private boolean isFinished;

  public TCCGuidanceResponseDTO(TCCGuidance tccGuidance) {
    this.id = tccGuidance.getId();
    this.studentId = tccGuidance.getStudent().getId();
    this.professorId = tccGuidance.getProfessor().getId();
    this.period = tccGuidance.getPeriod();
    this.isFinished = tccGuidance.isFinished();
  }

  public Long getId() {
    return id;
  }

  public Long getStudentId() {
    return studentId;
  }

  public Long getProfessorId() {
    return professorId;
  }

  public String getPeriod() {
    return period;
  }

  public boolean isFinished() {
    return isFinished;
  }
}
