package com.ufcg.psoft.tccmatch.dto.tccGuidances;

public class CreateTCCGuidanceDTO {

  private Long studentId;
  private Long professorId;
  private Long tccSubjectId;
  private String period;

  public CreateTCCGuidanceDTO(Long studentId, Long professorId, Long tccSubjectId, String period) {
    this.studentId = studentId;
    this.professorId = professorId;
    this.tccSubjectId = tccSubjectId;
    this.period = period;
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
}
