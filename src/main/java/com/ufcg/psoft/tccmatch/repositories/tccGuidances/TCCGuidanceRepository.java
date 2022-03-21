package com.ufcg.psoft.tccmatch.repositories.tccGuidances;

import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCGuidanceRepository extends JpaRepository<TCCGuidance, Long> {
  List<TCCGuidance> findAllByPeriod(String period);
  List<TCCGuidance> findAllByIsFinished(boolean isFinished);
  List<TCCGuidance> findAllByPeriodAndIsFinished(String period, boolean isFinished);
}
