package com.ufcg.psoft.tccmatch.repositories.tccSubjects;

import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import com.ufcg.psoft.tccmatch.models.users.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCSubjectRepository extends JpaRepository<TCCSubject, Long> {
  Optional<TCCSubject> findByTitle(String title);
  Set<TCCSubject> findByCreatedBy_Id(Long createdById);
  Set<TCCSubject> findByCreatedBy_Type(User.Type userType);
}
