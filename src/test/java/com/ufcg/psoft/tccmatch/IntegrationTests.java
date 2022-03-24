package com.ufcg.psoft.tccmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.tccmatch.dto.tccGuidanceRequests.CreateTCCGuidanceRequestRequestDTO;
import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateProfessorDTO;
import com.ufcg.psoft.tccmatch.dto.users.CreateStudentDTO;
import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.tccGuidanceRequest.TCCGuidanceRequest;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.services.sessions.AuthenticationService;
import com.ufcg.psoft.tccmatch.services.tccGuidanceRequest.TCCGuidanceRequestService;
import com.ufcg.psoft.tccmatch.services.tccGuidances.TCCGuidanceService;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class IntegrationTests {

  @Value("${users.default-coordinator.email}")
  protected String defaultCoordinatorEmail;

  @Value("${users.default-coordinator.password}")
  protected String defaultCoordinatorPassword;

  protected String studentEmail = "student@email.com";
  protected String studentRawPassword = "12345678";
  protected String studentName = "Student";
  protected String studentRegistryNumber = "111000111";
  protected String studentCompletionPeriod = "2024.1";

  protected String professorEmail = "professor@email.com";
  protected String professorRawPassword = "12345678";
  protected String professorName = "Professor";
  protected Set<String> professorLaboratories = new HashSet<>();

  protected String tccSubjectTitle = "IA: Salvadora da terra, ou fim dos tempos?";
  protected String tccSubjectDescription =
    "Um estudo sobre as diversas implicações do avanço de IA na tecnologia.";
  protected String tccSubjectStatus = "Nas etapas finais...";
  protected Set<FieldOfStudy> tccSubjectFieldsOfStudy = new HashSet<>();

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private ProfessorService professorService;

  @Autowired
  private TCCSubjectService tccSubjectService;

  @Autowired
  private TCCGuidanceRequestService tccGuidanceRequestService;

  @Autowired
  private TCCGuidanceService tccGuidanceService;

  protected TCCGuidance createMockTCCGuidance(
    long studentId,
    long professorId,
    long tccSubjectId,
    String period
  ) {
    CreateTCCGuidanceDTO requestDTO = new CreateTCCGuidanceDTO(
      studentId,
      professorId,
      tccSubjectId,
      period
    );
    return tccGuidanceService.createTCCGuidance(requestDTO);
  }

  protected TCCGuidanceRequest createMockTCCGuidanceRequest(
    long tccSubjectId,
    long professorId,
    Student issuingStudent
  ) {
    CreateTCCGuidanceRequestRequestDTO requestDTO = new CreateTCCGuidanceRequestRequestDTO(
      tccSubjectId,
      professorId
    );
    return tccGuidanceRequestService.createTCCGuidanceRequest(requestDTO, issuingStudent);
  }

  protected TCCSubject createMockTCCSubject(User tccSubjectCreator) {
    CreateTCCSubjectRequestDTO createTCCSubjectRequestDTO = new CreateTCCSubjectRequestDTO(
      tccSubjectTitle,
      tccSubjectDescription,
      tccSubjectStatus,
      tccSubjectFieldsOfStudy
    );
    return tccSubjectService.createTCCSubject(createTCCSubjectRequestDTO, tccSubjectCreator);
  }

  protected Professor createMockProfessor() {
    CreateProfessorDTO createProfessorDTO = new CreateProfessorDTO(
      professorEmail,
      professorRawPassword,
      professorName,
      professorLaboratories
    );
    return professorService.createProfessor(createProfessorDTO);
  }

  protected Student createMockStudent() {
    CreateStudentDTO createStudentDTO = new CreateStudentDTO(
      studentEmail,
      studentRawPassword,
      studentName,
      studentRegistryNumber,
      studentCompletionPeriod
    );
    return studentService.createStudent(createStudentDTO);
  }

  protected String loginWithDefaultCoordinator() {
    return login(defaultCoordinatorEmail, defaultCoordinatorPassword);
  }

  protected String loginWithMockStudent() {
    return login(studentEmail, studentRawPassword);
  }

  protected String loginWithMockProfessor() {
    return login(professorEmail, professorRawPassword);
  }

  protected String login(String email, String password) {
    String token = authenticationService.loginWithEmailAndPassword(email, password);
    return token;
  }

  protected MockHttpServletRequestBuilder authenticated(
    MockHttpServletRequestBuilder builder,
    String token
  ) {
    return builder.header("Authorization", String.format("Bearer %s", token));
  }

  protected String toJSON(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  protected <Response> Response fromJSON(String value, Class<Response> valueType)
    throws JsonProcessingException {
    return objectMapper.readValue(value, valueType);
  }
}
