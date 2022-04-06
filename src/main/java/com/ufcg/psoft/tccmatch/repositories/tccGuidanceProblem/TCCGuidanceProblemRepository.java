package com.ufcg.psoft.tccmatch.repositories.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCGuidanceProblemRepository extends JpaRepository<TCCGuidanceProblem, Long> {
  List<TCCGuidanceProblem> findAllByTccGuidance_Period(String period);
}
