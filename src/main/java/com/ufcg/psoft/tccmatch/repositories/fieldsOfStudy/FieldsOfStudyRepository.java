package com.ufcg.psoft.tccmatch.repositories.fieldsOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;

@Repository
public interface FieldsOfStudyRepository extends JpaRepository<FieldOfStudy, Long>{
     Optional<FieldOfStudy> findByName(String name);

}
