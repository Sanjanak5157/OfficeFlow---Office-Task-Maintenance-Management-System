package com.officeflow.service;

/**
 * Interface demonstrating OOP Polymorphism for score and grade calculation.
 */
public interface EvaluationScorer {

    /**
     * Calculates weighted composite score.
     * SLA compliance (35%) + System Uptime (25%) + Task Completion (25%) + Teamwork (15%)
     */
    double calculateOverallScore(double sla, double uptime, double completion, double teamwork);

    /**
     * Determines letter grade from numeric score: A+, A, B, C, or D.
     */
    String determineGrade(double overallScore);
}
