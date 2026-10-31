package com.officeflow.service.impl;

import com.officeflow.entity.MonthlyReport;
import com.officeflow.entity.User;
import com.officeflow.repository.MonthlyReportRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final MonthlyReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(MonthlyReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MonthlyReport> getAllReports() {
        return reportRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public MonthlyReport saveReport(MonthlyReport report) {
        if (report.getUser() == null && report.getUserId() != null) {
            User author = userRepository.findById(report.getUserId()).orElse(null);
            report.setUser(author);
        } else if (report.getUser() == null) {
            report.setUser(userRepository.findById(1L).orElse(null));
        }
        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
