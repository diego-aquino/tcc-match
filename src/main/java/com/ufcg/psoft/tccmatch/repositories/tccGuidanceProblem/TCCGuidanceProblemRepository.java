package com.ufcg.psoft.tccmatch.repositories.tccGuidanceProblem;

import com.ufcg.psoft.tccmatch.models.tccGuidanceProblem.TCCGuidanceProblem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCGuidanceProblemRepository extends JpaRepository<TCCGuidanceProblem, Long> {
}
