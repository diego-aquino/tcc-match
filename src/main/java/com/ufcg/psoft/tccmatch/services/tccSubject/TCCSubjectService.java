package com.ufcg.psoft.tccmatch.services.tccSubject;

import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccSubjects.TCCSubjectRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCSubjectService {

  @Autowired
  TCCSubjectRepository tccSubjectRepository;

  public Optional<TCCSubject> findTCCSubjectByTitle(String title) {
    return tccSubjectRepository.findByTitle(title);
  }

  public Optional<TCCSubject> findTCCSubjectById(Long id) {
    return tccSubjectRepository.findById(id);
  }

  public TCCSubject createTCCSubject(CreateTCCSubjectRequestDTO tccSubjectDTO, User user) {
    TCCSubject tccSubject = new TCCSubject(
      tccSubjectDTO.getTitle(),
      tccSubjectDTO.getDescription(),
      tccSubjectDTO.getStatus(),
      tccSubjectDTO.getFieldsOfStudy(),
      user
    );

    tccSubjectRepository.save(tccSubject);

    return tccSubject;
  }

  public Set<TCCSubject> listTCCSubjects(User user) {
    return tccSubjectRepository.findByCreatedBy_Type(user.TCCSubjectCreatedByTypeSearch());
  }

  public Set<TCCSubject> listTCCSubjectsByUser(long createdById) {
    return tccSubjectRepository.findByCreatedBy_Id(createdById);
  }

  public User getCreatedBySubjectId(long tccSubjectId) {
    Optional<TCCSubject> tccSubject = findTCCSubjectById(tccSubjectId);

    return tccSubject.get().getCreatedBy();
  }
}
