package com.ufcg.psoft.tccmatch.services.tccSubject;

import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.tccSubjects.TCCSubjectRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCCSubjectService {

  @Autowired
  TCCSubjectRepository tccSubjectRepository;

  private static final Map<User.Type, User.Type> TCC_SUBJECT_SEARCH_BY_USER_TYPE;

  static {
    Map<User.Type, User.Type> newMap = new HashMap<>();
    newMap.put(User.Type.PROFESSOR, User.Type.STUDENT);
    newMap.put(User.Type.STUDENT, User.Type.PROFESSOR);
    TCC_SUBJECT_SEARCH_BY_USER_TYPE = Collections.unmodifiableMap(newMap);
  }

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
    User.Type TCCSubjectCreatedByTypeToList = TCC_SUBJECT_SEARCH_BY_USER_TYPE.get(user.getType());
    return tccSubjectRepository.findByCreatedBy_Type(TCCSubjectCreatedByTypeToList);
  }

  public Set<TCCSubject> listTCCSubjectsByUser(long createdById) {
    return tccSubjectRepository.findByCreatedBy_Id(createdById);
  }

  public User getCreatedBySubjectId(long tccSubjectId) {
    Optional<TCCSubject> tccSubject = findTCCSubjectById(tccSubjectId);

    return tccSubject.get().getCreatedBy();
  }
}
