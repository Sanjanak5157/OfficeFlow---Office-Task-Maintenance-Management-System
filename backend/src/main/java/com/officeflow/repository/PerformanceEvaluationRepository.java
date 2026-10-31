package com.officeflow.repository;

import com.officeflow.entity.PerformanceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceEvaluationRepository extends JpaRepository<PerformanceEvaluation, Long> {
    List<PerformanceEvaluation> findAllByOrderByCreatedAtDesc();
    List<PerformanceEvaluation> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<PerformanceEvaluation> findByEvaluationMonth(String evaluationMonth);
}
