package com.ufcg.psoft.tccmatch.dto.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.TCCGuidanceResponseDTO;
import com.ufcg.psoft.tccmatch.dto.users.UserResponseDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem.Category;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Arrays;
import java.util.List;

public class TCCGuidanceProblemResponseDTO {

  private Long id;
  private Category category;
  private String description;
  private TCCGuidance tccGuidance;
  private User createdBy;

  public TCCGuidanceProblemResponseDTO(TCCGuidanceProblem tccGuidanceProblem) {
    this.id = tccGuidanceProblem.getId();
    this.category = tccGuidanceProblem.getCategory();
    this.description = tccGuidanceProblem.getDescription();
    this.tccGuidance = tccGuidanceProblem.getTCCGuidance();
    this.createdBy = tccGuidanceProblem.getCreatedBy();
  }

  public Long getId() {
    return id;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public TCCGuidanceResponseDTO getTccGuidance() {
    return new TCCGuidanceResponseDTO(tccGuidance);
  }

  public UserResponseDTO getCreatedBy() {
    return new UserResponseDTO(createdBy);
  }

  public static List<TCCGuidanceProblemResponseDTO> fromTCCGuidanceProblems(
    List<TCCGuidanceProblem> tccGuidanceProblems
  ) {
    return Arrays.asList(
      tccGuidanceProblems
        .stream()
        .map(TCCGuidanceProblemResponseDTO::new)
        .toArray(TCCGuidanceProblemResponseDTO[]::new)
    );
  }
}
