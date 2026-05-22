package com.diogenes.repository;

import com.diogenes.model.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
    Optional<SurveyResult> findTopByOrderByCreatedAtDesc();
}
