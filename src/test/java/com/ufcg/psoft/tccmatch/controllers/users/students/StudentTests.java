package com.ufcg.psoft.tccmatch.controllers.users.students;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class StudentTests extends IntegrationTests {

  protected String studentEmail = "student@email.com";
  protected String studentRawPassword = "12345678";
  protected String studentName = "Student";
  protected String studentRegistryNumber = "111000111";
  protected String studentCompletionPeriod = "2024.1";
}
