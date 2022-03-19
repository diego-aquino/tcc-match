package com.ufcg.psoft.tccmatch.models.tccGuidanceProblem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.ufcg.psoft.tccmatch.models.users.User;

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

  @Transient
  private Category category;

  private String description;

  @ManyToOne
  private User createdBy;

  public TCCGuidanceProblem() {
  };

  public TCCGuidanceProblem(Category category, String description, User createdBy) {
    this.category = category;
    this.description = description;
    this.createdBy = createdBy;
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

  public User getCreatedBy() {
    return createdBy;
  }
}
