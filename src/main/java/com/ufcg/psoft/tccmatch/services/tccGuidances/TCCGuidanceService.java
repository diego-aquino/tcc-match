package com.ufcg.psoft.tccmatch.services.tccGuidances;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.repositories.tccGuidances.TCCGuidanceRepository;
import com.ufcg.psoft.tccmatch.services.Validator;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCGuidanceService {

  @Autowired
  private StudentService studentService;

  @Autowired
  private ProfessorService professorService;

  @Autowired
  private TCCSubjectService tccSubjectService;

  @Autowired
  private TCCGuidanceRepository tccGuidanceRepository;

  @Autowired
  private Validator validator;

  public TCCGuidance createTCCGuidance(CreateTCCGuidanceDTO tccGuidanceDTO) {
    Student student = studentService.findByIdOrThrow(tccGuidanceDTO.getStudentId());
    Professor professor = professorService.findByIdOrThrow(tccGuidanceDTO.getProfessorId());
    TCCSubject tccSubject = tccSubjectService.findTCCSubjectByIdOrThrow(
      tccGuidanceDTO.getTccSubjectId()
    );
    String period = validator.validatePeriod(tccGuidanceDTO.getPeriod());

    TCCGuidance tccGuidance = new TCCGuidance(student, professor, tccSubject, period);
    tccGuidanceRepository.save(tccGuidance);
    return tccGuidance;
  }
}
