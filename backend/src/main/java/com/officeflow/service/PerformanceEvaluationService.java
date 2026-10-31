package com.officeflow.service;

import com.officeflow.entity.PerformanceEvaluation;
import java.util.List;

public interface PerformanceEvaluationService {
    List<PerformanceEvaluation> getAllEvaluations();
    PerformanceEvaluation saveEvaluation(PerformanceEvaluation evaluation);
}
