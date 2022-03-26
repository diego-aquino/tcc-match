package com.ufcg.psoft.tccmatch.services.tccGuidances;

import com.ufcg.psoft.tccmatch.dto.tccGuidances.CreateTCCGuidanceDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccGuidances.TCCGuidanceNotFoundException;
import com.ufcg.psoft.tccmatch.exceptions.users.professors.ProfessorNotAvailableForTCCGuidancesException;
import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.repositories.tccGuidances.TCCGuidanceRepository;
import com.ufcg.psoft.tccmatch.services.Validator;
import com.ufcg.psoft.tccmatch.services.tccSubject.TCCSubjectService;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    if (professor.getGuidanceQuota() == 0) {
      throw new ProfessorNotAvailableForTCCGuidancesException();
    }

    TCCGuidance tccGuidance = new TCCGuidance(student, professor, tccSubject, period);
    tccGuidanceRepository.save(tccGuidance);

    professorService.incrementGuidanceQuota(professor, -1);

    return tccGuidance;
  }

  public TCCGuidance finishTCCGuidance(Long tccGuidanceId) {
    Optional<TCCGuidance> optionalTCCGuidance = tccGuidanceRepository.findById(tccGuidanceId);

    if (optionalTCCGuidance.isEmpty()) {
      throw new TCCGuidanceNotFoundException();
    }

    TCCGuidance tccGuidance = optionalTCCGuidance.get();
    tccGuidance.markAsFinished();
    tccGuidanceRepository.save(tccGuidance);

    professorService.incrementGuidanceQuota(tccGuidance.getProfessor(), 1);

    return tccGuidance;
  }

  public List<TCCGuidance> listTCCGuidances(Optional<String> period, Optional<Boolean> isFinished) {
    if (period.isPresent() && isFinished.isPresent()) {
      return tccGuidanceRepository.findAllByPeriodAndIsFinished(period.get(), isFinished.get());
    }
    if (period.isPresent()) {
      return tccGuidanceRepository.findAllByPeriod(period.get());
    }
    if (isFinished.isPresent()) {
      return tccGuidanceRepository.findAllByIsFinished(isFinished.get());
    }
    return tccGuidanceRepository.findAll();
  }

  public List<TCCGuidance> filterTCCGuidancesByProfessor(
    List<TCCGuidance> tccGuidances,
    Long professorId
  ) {
    return Arrays.asList(
      tccGuidances
        .stream()
        .filter(tccGuidance -> tccGuidance.getProfessor().getId().equals(professorId))
        .toArray(TCCGuidance[]::new)
    );
  }

  public List<TCCGuidance> listAllTCCGuidances() {
    return tccGuidanceRepository.findAll();
  }

  public Optional<TCCGuidance> findTCCGuidanceById(Long id) {
    return tccGuidanceRepository.findById(id);
  }
}
