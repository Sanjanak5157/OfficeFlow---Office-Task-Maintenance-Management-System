package com.officeflow.service.impl;

import com.officeflow.repository.*;
import com.officeflow.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final NoteRepository noteRepository;
    private final ProjectRepoRepository projectRepoRepository;
    private final MonthlyReportRepository reportRepository;
    private final IdeaProposalRepository ideaProposalRepository;
    private final TaskSheetRepository taskSheetRepository;
    private final TaskRowRepository taskRowRepository;
    private final OrgEmailLogRepository emailLogRepository;

    public DashboardServiceImpl(NoteRepository noteRepository,
                                ProjectRepoRepository projectRepoRepository,
                                MonthlyReportRepository reportRepository,
                                IdeaProposalRepository ideaProposalRepository,
                                TaskSheetRepository taskSheetRepository,
                                TaskRowRepository taskRowRepository,
                                OrgEmailLogRepository emailLogRepository) {
        this.noteRepository = noteRepository;
        this.projectRepoRepository = projectRepoRepository;
        this.reportRepository = reportRepository;
        this.ideaProposalRepository = ideaProposalRepository;
        this.taskSheetRepository = taskSheetRepository;
        this.taskRowRepository = taskRowRepository;
        this.emailLogRepository = emailLogRepository;
    }

    @Override
    public Map<String, Object> getDashboardStats(Long userId) {
        long totalNotes = noteRepository.count();
        long totalProjects = projectRepoRepository.count();
        long totalReports = reportRepository.count();
        long totalIdeas = ideaProposalRepository.count();
        long emailsDispatched = emailLogRepository.count();
        long activeSheets = taskSheetRepository.count();
        long pendingTasks = taskRowRepository.findByStatus("PENDING").size();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalNotes", totalNotes);
        stats.put("totalProjects", totalProjects);
        stats.put("currentStreakDays", 18);
        stats.put("totalReports", totalReports);
        stats.put("totalIdeaProposals", totalIdeas);
        stats.put("teamAverageScore", 93.5);
        stats.put("activeTaskSheets", activeSheets);
        stats.put("pendingExcelTasks", pendingTasks);
        stats.put("orgEmailsDispatched", emailsDispatched);
        stats.put("hasOrgEmailAccess", true);
        stats.put("userRole", "TEAM_LEAD");

        return stats;
    }
}
