package com.ufcg.psoft.tccmatch.dto.tccGuidances;

import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import java.util.Arrays;
import java.util.List;

public class TCCGuidanceResponseDTO {

  private Long id;
  private Long studentId;
  private Long professorId;
  private Long tccSubjectId;
  private String period;
  private boolean isFinished;

  public TCCGuidanceResponseDTO(TCCGuidance tccGuidance) {
    this.id = tccGuidance.getId();
    this.studentId = tccGuidance.getStudent().getId();
    this.professorId = tccGuidance.getProfessor().getId();
    this.tccSubjectId = tccGuidance.getTCCSubject().getId();
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

  public Long getTccSubjectId() {
    return tccSubjectId;
  }

  public String getPeriod() {
    return period;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public static List<TCCGuidanceResponseDTO> fromTCCGuidances(List<TCCGuidance> tccGuidances) {
    return Arrays.asList(
      tccGuidances.stream().map(TCCGuidanceResponseDTO::new).toArray(TCCGuidanceResponseDTO[]::new)
    );
  }
}
