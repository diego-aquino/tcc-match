package com.ufcg.psoft.tccmatch.controllers.users.professors;

import com.ufcg.psoft.tccmatch.IntegrationTests;
import java.util.HashSet;
import java.util.Set;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class ProfessorTests extends IntegrationTests {

  protected String professorEmail = "professor@email.com";
  protected String professorRawPassword = "12345678";
  protected String professorName = "Professor";
  protected Set<String> professorLaboratories = new HashSet<>();
}
