package com.diogenes.repository;

import com.diogenes.model.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    Optional<AssessmentResult> findTopByOrderByCreatedAtDesc();
}
