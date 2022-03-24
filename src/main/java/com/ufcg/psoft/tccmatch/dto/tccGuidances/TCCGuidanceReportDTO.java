package com.ufcg.psoft.tccmatch.dto.tccGuidances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.ufcg.psoft.tccmatch.dto.fieldsOfStudy.FieldOfStudyResponseDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;

public class TCCGuidanceReportDTO {

    private Long id;
    private List<FieldOfStudyResponseDTO> tccFieldOfStudy;
    private String period;
    private boolean isFinished;
  
    public TCCGuidanceReportDTO(TCCGuidance tccGuidance) {
      this.id = tccGuidance.getId();
      this.tccFieldOfStudy = convertSetFields(tccGuidance.getTCCSubject().getFieldsOfStudy());
      this.period = tccGuidance.getPeriod();
      this.isFinished = tccGuidance.isFinished();
    }
  
  private List<FieldOfStudyResponseDTO> convertSetFields(Set<FieldOfStudy>fields){
    return Arrays.asList(
      (new ArrayList<>(fields)).stream().map(FieldOfStudyResponseDTO::new).toArray(FieldOfStudyResponseDTO[]::new)
    );
  }
    
  public Long getId() {
    return id;
  }

  public List<FieldOfStudyResponseDTO> getTccFieldOfStudy() {
    return this.tccFieldOfStudy;
  }

  public String getPeriod() {
    return period;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public static List<TCCGuidanceReportDTO> fromTCCGuidances(List<TCCGuidance> tccGuidances) {
    return Arrays.asList(
      tccGuidances.stream().map(TCCGuidanceReportDTO::new).toArray(TCCGuidanceReportDTO[]::new)
    );
  }
}
