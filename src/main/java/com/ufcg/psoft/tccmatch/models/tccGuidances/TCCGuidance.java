package com.ufcg.psoft.tccmatch.models.tccGuidances;

import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class TCCGuidance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Student student;

  @ManyToOne(fetch = FetchType.EAGER)
  private Professor professor;

  @OneToOne(fetch = FetchType.EAGER)
  private TCCSubject tccSubject;

  private String period;

  private boolean isFinished;

  protected TCCGuidance() {}

  public TCCGuidance(Student student, Professor professor, TCCSubject tccSubject, String period) {
    this.student = student;
    this.professor = professor;
    this.tccSubject = tccSubject;
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

  public TCCSubject getTCCSubject() {
    return tccSubject;
  }

  public String getPeriod() {
    return period;
  }

  public boolean isFinished() {
    return isFinished;
  }
}
