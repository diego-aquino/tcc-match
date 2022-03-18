package com.ufcg.psoft.tccmatch.repositories.tccGuidances;

import com.ufcg.psoft.tccmatch.models.tccGuidances.TCCGuidance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCGuidanceRepository extends JpaRepository<TCCGuidance, Long> {}
