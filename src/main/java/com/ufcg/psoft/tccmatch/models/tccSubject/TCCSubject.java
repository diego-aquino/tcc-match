package com.ufcg.psoft.tccmatch.models.tccSubject;

import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class TCCSubject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String title;
  private String description;
  private String status;

  @ManyToMany
  private Set<String> fieldsOfStudy; //Change to fields of study

  @ManyToOne
  private User createdBy;

  public TCCSubject() {}

  public TCCSubject(
    String title,
    String description,
    String status,
    Set<String> fieldsOfStudy,
    User createdBy
  ) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.createdBy = createdBy;
    this.fieldsOfStudy = fieldsOfStudy;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public Set<String> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public User getCreatedBy() {
    return createdBy;
  }
}
