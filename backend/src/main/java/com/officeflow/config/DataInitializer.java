package com.officeflow.config;

import com.officeflow.entity.*;
import com.officeflow.enums.Role;
import com.officeflow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PACKAGE: com.officeflow.config
 * PURPOSE: Automatically seeds demo data on initial startup for SSMS (Microsoft SQL Server).
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(
            UserRepository userRepository,
            NoteRepository noteRepository,
            ProjectRepoRepository repoRepository,
            StreakLogRepository streakRepository,
            MonthlyReportRepository reportRepository,
            IdeaProposalRepository ideaRepository,
            PerformanceEvaluationRepository evaluationRepository,
            TaskSheetRepository sheetRepository,
            EmailAccessGrantRepository grantRepository,
            OrgEmailLogRepository emailLogRepository) {

        return args -> {
            if (userRepository.count() > 0) {
                System.out.println("[OfficeFlow] Existing SSMS database records detected. Skipping auto-seeding.");
                return;
            }

            System.out.println("[OfficeFlow] Initializing SSMS (Microsoft SQL Server) with demo office data...");

            // 1. Create Default Users (Lead + Members)
            User lead = new User("Alex Rivera", "lead@officeflow.com", "admin123", Role.TEAM_LEAD, "Facility Operations", "Lead Facilities Architect");
            lead.setOrganizationEmail("alex.rivera@corp.officeflow.com");
            lead.setHasOrgEmailAccess(true);
            lead.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150");
            userRepository.save(lead);

            User sarah = new User("Sarah Connor", "sarah@officeflow.com", "user123", Role.TEAM_MEMBER, "Electrical & HVAC", "Senior Systems Technician");
            sarah.setOrganizationEmail("sarah.connor@corp.officeflow.com");
            sarah.setHasOrgEmailAccess(true);
            sarah.setAvatarUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150");
            userRepository.save(sarah);

            User david = new User("David Chen", "david@officeflow.com", "user123", Role.TEAM_MEMBER, "IT Network & Servers", "Network Maintenance Engineer");
            david.setOrganizationEmail("david.chen@corp.officeflow.com");
            david.setHasOrgEmailAccess(true);
            david.setAvatarUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150");
            userRepository.save(david);

            User maya = new User("Maya Patel", "maya@officeflow.com", "user123", Role.TEAM_MEMBER, "Workplace Safety & Compliance", "Safety Operations Associate");
            maya.setHasOrgEmailAccess(false);
            maya.setAvatarUrl("https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150");
            userRepository.save(maya);

            // 2. Feature 1: Daily Notes
            noteRepository.save(new Note("Server Room HVAC Temperature Audit", 
                    "Inspected Zone 3 server rack intake temperature: 19.5°C. Secondary coolant cycle filter requires replacement on Friday. Scheduled HVAC technician.", 
                    "Maintenance", true, "#8b5cf6", lead));
            noteRepository.save(new Note("Weekly Office Floor Power Distribution Log", 
                    "Checked 4th floor UPS units. Unit B battery health at 96%. All emergency backup switches calibrated for automated generator cutover test next week.", 
                    "Urgent", false, "#ec4899", lead));
            noteRepository.save(new Note("Cafeteria Plumbing & Water Filtration Check", 
                    "Replaced dual carbon cartridge in water purification line. Clean water flow restored to 45 L/min. Safe water test parameters verified.", 
                    "Checklist", false, "#3b82f6", sarah));
            noteRepository.save(new Note("Fire Suppression Sensor Network Verification", 
                    "All 32 optical smoke sensors in Block B tested successfully via master telemetry panel. No telemetry packets dropped over RS-485 bus.", 
                    "Daily Standup", true, "#10b981", david));

            // 3. Feature 2: Project Repos
            ProjectRepo hvacRepo = new ProjectRepo("IoT-Smart-HVAC-Monitor", 
                    "Real-time telemetry and automated climate control integration across 5 office blocks.", 
                    "https://github.com/officeflow/smart-hvac-monitor", "https://hvac-telemetry.corp.officeflow.com", 
                    "Java 17, Spring Boot, MQTT, SSMS", "IT Support", lead);
            repoRepository.save(hvacRepo);

            ProjectRepo accessRepo = new ProjectRepo("Office-Access-Pass-Portal", 
                    "Digital badge access management system, visitor approvals, and QR security validation.", 
                    "https://github.com/officeflow/access-pass-portal", "https://pass.officeflow.com", 
                    "Spring Boot 3, SSMS (SQL Server), Tailwind CSS", "Security", lead);
            repoRepository.save(accessRepo);

            repoRepository.save(new ProjectRepo("Facility-Power-Telemetry", 
                    "High voltage load balancing dashboard and generator automatic failover logger.", 
                    "https://github.com/officeflow/facility-power-telemetry", "https://power.officeflow.com", 
                    "Java, Spring Data JPA, Chart.js, SSMS", "Facility", sarah));

            repoRepository.save(new ProjectRepo("Smart-Workstation-Booking", 
                    "Floor plan seat reservation and conference room projector hardware diagnostic manager.", 
                    "https://github.com/officeflow/workstation-desk-book", "https://desk.officeflow.com", 
                    "React, Spring Boot, SSMS", "Operations", david));

            // 4. Feature 3: Daily Streaks
            LocalDate today = LocalDate.now();
            streakRepository.save(new StreakLog(today.minusDays(4), "Floor 2 sensor calibration and server room check", 8.0, "COMPLETED", 14, "All clear", lead));
            streakRepository.save(new StreakLog(today.minusDays(3), "UPS load testing and battery diagnostics", 8.5, "COMPLETED", 15, "Zero downtime", lead));
            streakRepository.save(new StreakLog(today.minusDays(2), "Emergency exit lighting fixture inspection", 7.5, "COMPLETED", 16, "Replaced 2 LED fixtures", lead));
            streakRepository.save(new StreakLog(today.minusDays(1), "Water pressure regulation and pipeline audit", 8.0, "COMPLETED", 17, "Routine check clear", lead));
            streakRepository.save(new StreakLog(today, "Standup meeting and Weekly task sheet distribution", 8.0, "COMPLETED", 18, "Distributed sheet to team", lead));

            // 5. Feature 4: Monthly Report
            MonthlyReport report = new MonthlyReport("Comprehensive Facility Maintenance & Operations Report", "October 2026", "Facility Operations",
                    "<h2>1. Executive Summary</h2><p>The office infrastructure maintained an outstanding <b>99.94% facility uptime</b> across October 2026. A total of 142 routine and preventive maintenance tickets were closed ahead of target SLA.</p><h2>2. Key Systems Performance</h2><ul><li><b>HVAC Optimization:</b> Power usage decreased by 12.4% following automated cycle tuning.</li><li><b>IT Network & Server Room:</b> Backup generators successfully triggered 2 test cutovers without brownouts.</li><li><b>Safety Audits:</b> Fire safety & suppression compliance achieved 100% score on municipal inspection.</li></ul><h2>3. Budget Allocation</h2><p>Actual maintenance spend was $18,450 vs allocated budget of $22,000, achieving a 16.1% cost optimization.</p>",
                    "Achieved 99.94% facility uptime and closed 142 tickets with 16.1% budget savings.",
                    142, new BigDecimal("18450.00"), "APPROVED", lead);
            report.setReviewedBy("Chief Operations Officer");
            reportRepository.save(report);

            // 6. Feature 5: Idea Proposals
            IdeaProposal prop1 = new IdeaProposal("IoT-Smart-HVAC-Monitor",
                    "Old Idea: Technicians manually walk around each office floor with digital thermometers every morning at 9:00 AM and enter numbers into a paper binder.",
                    "New Suggestion: Mount low-power Zigbee/LoRaWAN temperature and humidity sensors on each ceiling grid that broadcast real-time metrics to our Spring Boot backend every 5 minutes with automated Telegram/Email alerts when thresholds exceed 24°C.",
                    "Eliminates 3 hours of manual technician rounds per day, provides instant leak/overheating detection, and prevents server room hardware thermal throttling.",
                    "HIGH", sarah);
            prop1.setStatus("ACCEPTED");
            prop1.setLeadFeedback("Approved by Team Lead Alex. Budget approved for 40 wireless sensors.");
            prop1.setProjectRepo(hvacRepo);
            ideaRepository.save(prop1);

            IdeaProposal prop2 = new IdeaProposal("Office-Access-Pass-Portal",
                    "Old Idea: Physical laminated visitor badges written by security desk guards using Sharpie markers.",
                    "New Suggestion: Self-service iPad kiosk with WebRTC webcam and instant QR code passes generated directly into visitor phone wallets.",
                    "Zero reception queue bottlenecks, real-time fire evacuation headcount tracking, and enhanced building security compliance.",
                    "CRITICAL", david);
            prop2.setStatus("UNDER_REVIEW");
            prop2.setLeadFeedback("Under review with Facilities & Security Director.");
            prop2.setProjectRepo(accessRepo);
            ideaRepository.save(prop2);

            // 7. Feature 6: Performance Evaluations
            PerformanceEvaluation evalSarah = new PerformanceEvaluation();
            evalSarah.setUser(sarah);
            evalSarah.setEvaluator(lead);
            evalSarah.setEvaluationMonth("October 2026");
            evalSarah.setMaintenanceSlaScore(96);
            evalSarah.setUptimeReliabilityScore(98);
            evalSarah.setTaskCompletionScore(94);
            evalSarah.setTeamworkInitiativeScore(95);
            evalSarah.calculateOverallScoreAndGrade();
            evalSarah.setStrengths("Proactive emergency response during transformer surge. Outstanding initiative in drafting the wireless HVAC sensor proposal.");
            evalSarah.setAreasOfImprovement("Continue mentoring junior apprentices on high-voltage 3-phase wiring safety protocols.");
            evalSarah.setLeadComments("Exceptional work throughout the entire maintenance cycle. Key pillar of the facility engineering team.");
            evaluationRepository.save(evalSarah);

            PerformanceEvaluation evalDavid = new PerformanceEvaluation();
            evalDavid.setUser(david);
            evalDavid.setEvaluator(lead);
            evalDavid.setEvaluationMonth("October 2026");
            evalDavid.setMaintenanceSlaScore(92);
            evalDavid.setUptimeReliabilityScore(95);
            evalDavid.setTaskCompletionScore(90);
            evalDavid.setTeamworkInitiativeScore(88);
            evalDavid.calculateOverallScoreAndGrade();
            evalDavid.setStrengths("Rapid resolution of server rack network drops and fiber patch reliability.");
            evalDavid.setAreasOfImprovement("Improve documentation updates on repository readmes after code merges.");
            evalDavid.setLeadComments("Solid technical performance and high reliability during night maintenance shifts.");
            evaluationRepository.save(evalDavid);

            // 8. Feature 7: Team Excel Task Sheet & Rows
            TaskSheet sheet = new TaskSheet("Weekly Office Infrastructure & Preventive Maintenance Sheet", 
                    "Active team spreadsheet: each member update assigned rows with daily work status, time spent, and blockers.", 
                    today.plusDays(3), lead);

            sheet.addRow(new TaskRow("Replace HEPA Air Filters in Executive Wing", "HVAC Air Quality", "HIGH", "Sarah Connor", "Completed 8 of 10 air handler units. Filter batch #B92 installed. Final 2 units scheduled for 3 PM.", 3.5, "IN_PROGRESS", sarah));
            sheet.addRow(new TaskRow("Server Room Rack 4 Cable Tidying & Patch Panel Audit", "IT Infrastructure", "MEDIUM", "David Chen", "Finished patch panel labeling. Cat6A cables organized with velcro wraps. All links verified at 10 Gbps.", 2.0, "COMPLETED", david));
            sheet.addRow(new TaskRow("Test Emergency Evacuation Sirens & Strobe Horns", "Safety & Compliance", "URGENT", "Maya Patel", "Tested floor 1 through 3 horns. Floor 2 east strobe had loose wiring, tightened terminal screws and verified audio dB level.", 4.0, "COMPLETED", maya));
            sheet.addRow(new TaskRow("Main Generator Fuel Reserve & Coolant Check", "Power Backup", "HIGH", "Sarah Connor", "Diesel tank capacity measured at 92%. Coolant glycol concentration within optimal 50/50 spec.", 1.5, "COMPLETED", sarah));
            sheet.addRow(new TaskRow("Conference Room Crestron Display Firmware Upgrade", "AV Maintenance", "LOW", "David Chen", "Pending team member input. Waiting for conference room 402 to be vacated after board meeting.", 0.0, "PENDING", david));

            sheetRepository.save(sheet);

            // 9. Feature 8: Email Access Grant and Sent Emails
            grantRepository.save(new EmailAccessGrant("sarah.connor@corp.officeflow.com", "SEND_LEAVE,SEND_ANNOUNCEMENT,SEND_BROADCAST", "Granted full office organization broadcast & leave dispatch access by Team Lead Alex.", lead, sarah));
            grantRepository.save(new EmailAccessGrant("david.chen@corp.officeflow.com", "SEND_LEAVE,SEND_ANNOUNCEMENT", "Granted facility leave and maintenance alerts access.", lead, david));

            emailLogRepository.save(new OrgEmailLog("sarah.connor@corp.officeflow.com", "ALL_ORGANIZATION", "all-staff@corp.officeflow.com", "MAINTENANCE_ALERT",
                    "[MAINTENANCE NOTICE] Scheduled Water Main Valve Servicing Tonight 9 PM - 11 PM",
                    "Dear Officeflow Team,\n\nPlease note that the main domestic water line on Floors 2 and 3 will undergo routine valve seal replacement tonight between 21:00 and 23:00. Restroom water dispensers will be temporarily diverted.\n\nBest regards,\nSarah Connor", sarah));

            emailLogRepository.save(new OrgEmailLog("david.chen@corp.officeflow.com", "TEAM_LEAD", "alex.rivera@corp.officeflow.com, hr@corp.officeflow.com", "LEAVE_REQUEST",
                    "[LEAVE REQUEST] Planned Annual Leave - Nov 12 to Nov 15 (4 Days)",
                    "Hi Alex & HR Team,\n\nI would like to apply for 4 days of annual leave from November 12th to November 15th, 2026. All server maintenance schedules for that week have been delegated to Sarah Connor.\n\nThank you,\nDavid Chen", david));

            System.out.println("[OfficeFlow] SSMS database initialized successfully!");
        };
    }
}
