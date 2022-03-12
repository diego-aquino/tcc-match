package com.ufcg.psoft.tccmatch.repositories.tccSubjects;

import com.ufcg.psoft.tccmatch.models.tccSubject.TCCSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCSubjectRepository extends JpaRepository<TCCSubject, Long> {}
