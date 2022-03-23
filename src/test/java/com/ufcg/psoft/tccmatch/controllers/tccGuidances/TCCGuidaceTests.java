package com.ufcg.psoft.tccmatch.controllers.tccGuidances;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class TCCGuidanceTests extends IntegrationTests {

  protected Student student;
  protected Professor professor;
  protected TCCSubject tccSubject;

  protected String coordinatorToken;

  protected String period = "2020.2";
}
