package com.officeflow.service;

import com.officeflow.entity.MonthlyReport;
import java.util.List;

public interface ReportService {
    List<MonthlyReport> getAllReports();
    MonthlyReport saveReport(MonthlyReport report);
    void deleteReport(Long id);
}
