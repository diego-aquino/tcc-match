package com.ufcg.psoft.tccmatch.dto.tccSubjects;

import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import java.util.Set;

public class ListTCCSubjectResponseDTO {

  private Set<TCCSubject> tccSubjects;

  public ListTCCSubjectResponseDTO(Set<TCCSubject> tccSubjects) {
    this.tccSubjects = tccSubjects;
  }

  public Set<TCCSubject> getTccSubjects() {
    return tccSubjects;
  }
}
