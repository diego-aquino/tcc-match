package com.ufcg.psoft.tccmatch.models.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.users.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TCCGuidanceProblem {

  public enum Category {
    UNAVAILABILITY,
    COMMUNICATION,
    ATTENDANCE,
    OTHER,
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Category category;

  private String description;

  @ManyToOne
  private TCCGuidance tccGuidance;

  @ManyToOne
  private User createdBy;

  public TCCGuidanceProblem() {}

  public TCCGuidanceProblem(
    Category category,
    String description,
    User createdBy,
    TCCGuidance tccGuidance
  ) {
    this.category = category;
    this.description = description;
    this.createdBy = createdBy;
    this.tccGuidance = tccGuidance;
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

  public void setDescription(String description) {
    this.description = description;
  }

  public TCCGuidance getTCCGuidance() {
    return tccGuidance;
  }

  public User getCreatedBy() {
    return createdBy;
  }
}
