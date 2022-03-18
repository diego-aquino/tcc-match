package com.ufcg.psoft.tccmatch.models.tccGuidances;

import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TCCGuidance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Student student;

  @ManyToOne
  private Professor professor;

  private String period;

  private boolean isFinished;

  protected TCCGuidance() {}

  public TCCGuidance(Student student, Professor professor, String period) {
    this.student = student;
    this.professor = professor;
    this.period = period;
    this.isFinished = false;
  }

  public Long getId() {
    return id;
  }

  public Student getStudent() {
    return student;
  }

  public Professor getProfessor() {
    return professor;
  }

  public String getPeriod() {
    return period;
  }

  public boolean isFinished() {
    return isFinished;
  }
}
