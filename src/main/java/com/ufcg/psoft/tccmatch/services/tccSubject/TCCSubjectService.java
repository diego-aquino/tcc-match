package com.ufcg.psoft.tccmatch.services.tccSubject;

import com.ufcg.psoft.tccmatch.dto.tccSubjects.CreateTCCSubjectRequestDTO;
import com.ufcg.psoft.tccmatch.exceptions.tccSubjects.TCCSubjectNotFoundException;
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

  public Optional<TCCSubject> findTCCSubjectByTitle(String tccSubjectTitle) {
    return tccSubjectRepository.findByTitle(tccSubjectTitle);
  }

  public Optional<TCCSubject> findTCCSubjectById(Long tccSubjectId) {
    return tccSubjectRepository.findById(tccSubjectId);
  }

  public TCCSubject findTCCSubjectByIdOrThrow(Long tccSubjectId) {
    Optional<TCCSubject> optionalUser = tccSubjectRepository.findById(tccSubjectId);
    if (optionalUser.isEmpty()) throw new TCCSubjectNotFoundException();
    return optionalUser.get();
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

  public Set<TCCSubject> listTCCSubjectsVisibleToUser(User user) {
    User.Type userTypeToSearch = TCC_SUBJECT_SEARCH_BY_USER_TYPE.get(user.getType());
    return tccSubjectRepository.findByCreatedBy_Type(userTypeToSearch);
  }

  public Set<TCCSubject> listTCCSubjectsCreatedByUser(long createdById) {
    return tccSubjectRepository.findByCreatedBy_Id(createdById);
  }
}
