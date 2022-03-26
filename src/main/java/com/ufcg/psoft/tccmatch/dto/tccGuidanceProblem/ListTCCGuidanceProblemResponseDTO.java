package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

import java.util.List;

public class ListTCCGuidanceProblemResponseDTO {

  private List<TCCGuidanceProblemResponseDTO> studentsTCCGuidanceProblems;
  private List<TCCGuidanceProblemResponseDTO> professorsTCCGuidanceProblems;

  public ListTCCGuidanceProblemResponseDTO(
    List<TCCGuidanceProblemResponseDTO> studentsTCCGuidanceProblems,
    List<TCCGuidanceProblemResponseDTO> professorsTCCGuidanceProblems
  ) {
    this.studentsTCCGuidanceProblems = studentsTCCGuidanceProblems;
    this.professorsTCCGuidanceProblems = professorsTCCGuidanceProblems;
  }

  public List<TCCGuidanceProblemResponseDTO> getStudentsTccGuidanceProblems() {
    return studentsTCCGuidanceProblems;
  }

  public List<TCCGuidanceProblemResponseDTO> getProfessorsTccGuidanceProblems() {
    return professorsTCCGuidanceProblems;
  }
}
