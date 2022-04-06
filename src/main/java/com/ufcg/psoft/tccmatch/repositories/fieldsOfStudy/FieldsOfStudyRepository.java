package com.ufcg.psoft.tccmatch.repositories.fieldsOfStudy;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldsOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {
  Optional<FieldOfStudy> findByName(String name);
}
