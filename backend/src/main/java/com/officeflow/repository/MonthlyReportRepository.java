package com.officeflow.repository;

import com.officeflow.entity.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {
    List<MonthlyReport> findAllByOrderByCreatedAtDesc();
    List<MonthlyReport> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<MonthlyReport> findByReportMonth(String reportMonth);
}
