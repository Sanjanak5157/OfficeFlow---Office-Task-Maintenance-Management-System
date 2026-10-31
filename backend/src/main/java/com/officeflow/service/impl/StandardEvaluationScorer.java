package com.officeflow.service.impl;

import com.officeflow.service.EvaluationScorer;
import org.springframework.stereotype.Component;

/**
 * Concrete implementation demonstrating OOP Polymorphism.
 * Computes weighted evaluation metrics using floating point arithmetic.
 */
@Component
public class StandardEvaluationScorer implements EvaluationScorer {

    // Defined weight constants: SLA (35%), Uptime (25%), Completion (25%), Teamwork (15%)
    private static final double WEIGHT_SLA = 0.35;
    private static final double WEIGHT_UPTIME = 0.25;
    private static final double WEIGHT_COMPLETION = 0.25;
    private static final double WEIGHT_TEAMWORK = 0.15;

    @Override
    public double calculateOverallScore(double sla, double uptime, double completion, double teamwork) {
        double composite = (sla * WEIGHT_SLA) +
                (uptime * WEIGHT_UPTIME) +
                (completion * WEIGHT_COMPLETION) +
                (teamwork * WEIGHT_TEAMWORK);

        // Round to 2 decimal places using Math function
        return Math.round(composite * 100.0) / 100.0;
    }

    @Override
    public String determineGrade(double overallScore) {
        if (overallScore >= 95.0) {
            return "A+";
        } else if (overallScore >= 85.0) {
            return "A";
        } else if (overallScore >= 75.0) {
            return "B";
        } else if (overallScore >= 60.0) {
            return "C";
        } else {
            return "D";
        }
    }
}
