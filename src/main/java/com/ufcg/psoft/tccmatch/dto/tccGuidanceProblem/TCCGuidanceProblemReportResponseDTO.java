package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

import java.util.List;

public class TCCGuidanceProblemReportResponseDTO {

  private List<TCCGuidanceProblemResponseDTO> studentProblems;
  private List<TCCGuidanceProblemResponseDTO> professorProblems;

  public TCCGuidanceProblemReportResponseDTO(
    List<TCCGuidanceProblemResponseDTO> studentProblems,
    List<TCCGuidanceProblemResponseDTO> professorProblems
  ) {
    this.studentProblems = studentProblems;
    this.professorProblems = professorProblems;
  }

  public List<TCCGuidanceProblemResponseDTO> getStudentProblems() {
    return studentProblems;
  }

  public List<TCCGuidanceProblemResponseDTO> getProfessorProblems() {
    return professorProblems;
  }
}
