package com.officeflow.service.impl;

import com.officeflow.entity.PerformanceEvaluation;
import com.officeflow.entity.User;
import com.officeflow.repository.PerformanceEvaluationRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.PerformanceEvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceEvaluationServiceImpl implements PerformanceEvaluationService {

    private final PerformanceEvaluationRepository evaluationRepository;
    private final UserRepository userRepository;

    public PerformanceEvaluationServiceImpl(PerformanceEvaluationRepository evaluationRepository, UserRepository userRepository) {
        this.evaluationRepository = evaluationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PerformanceEvaluation> getAllEvaluations() {
        return evaluationRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public PerformanceEvaluation saveEvaluation(PerformanceEvaluation evaluation) {
        if (evaluation.getUser() == null && evaluation.getUserId() != null) {
            User target = userRepository.findById(evaluation.getUserId()).orElse(null);
            evaluation.setUser(target);
        } else if (evaluation.getUser() == null) {
            evaluation.setUser(userRepository.findById(2L).orElse(null));
        }

        if (evaluation.getEvaluator() == null) {
            evaluation.setEvaluator(userRepository.findById(1L).orElse(null));
        }

        // Core Java calculation function
        evaluation.calculateOverallScoreAndGrade();

        return evaluationRepository.save(evaluation);
    }
}
