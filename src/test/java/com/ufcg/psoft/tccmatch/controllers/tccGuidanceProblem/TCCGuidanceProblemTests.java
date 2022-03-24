package com.ufcg.psoft.tccmatch.controllers.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class TCCGuidanceProblemTests extends IntegrationTests {

  protected Student student;
  protected Professor professor;
  protected TCCSubject tccSubject;
  protected String period = "2020.2";

  protected String category = "COMMUNICATION";
  protected String description = "A comunicação está nos atrapalhando.";
  protected TCCGuidance tccGuidance;

  protected String coordinatorToken;
  protected String studentToken;
  protected String professorToken;
}
