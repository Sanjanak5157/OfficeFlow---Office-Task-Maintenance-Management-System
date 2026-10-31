-- ==========================================================
-- OfficeFlow - Seed Data for SSMS (Microsoft SQL Server)
-- ==========================================================

USE officeflow_db;
GO

-- 1. Insert Default Demo Users (Team Lead and Team Members)
SET IDENTITY_INSERT dbo.users ON;

INSERT INTO dbo.users (id, name, email, password, role, department, designation, organization_email, has_org_email_access, avatar_url)
VALUES
(1, N'Alex Rivera', N'lead@officeflow.com', N'admin123', N'TEAM_LEAD', N'Facility Operations', N'Lead Facilities Architect', N'alex.rivera@corp.officeflow.com', 1, N'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150'),
(2, N'Sarah Connor', N'sarah@officeflow.com', N'user123', N'TEAM_MEMBER', N'Electrical & HVAC', N'Senior Systems Technician', N'sarah.connor@corp.officeflow.com', 1, N'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150'),
(3, N'David Chen', N'david@officeflow.com', N'user123', N'TEAM_MEMBER', N'IT Network & Servers', N'Network Maintenance Engineer', N'david.chen@corp.officeflow.com', 1, N'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150'),
(4, N'Maya Patel', N'maya@officeflow.com', N'user123', N'TEAM_MEMBER', N'Workplace Safety & Compliance', N'Safety Operations Associate', NULL, 0, N'https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150');

SET IDENTITY_INSERT dbo.users OFF;
GO

-- 2. Insert Daily Notes (Feature 1)
SET IDENTITY_INSERT dbo.daily_notes ON;

INSERT INTO dbo.daily_notes (id, user_id, title, content, category, is_pinned, color_tag)
VALUES
(1, 1, N'Server Room HVAC Temperature Audit', N'Inspected Zone 3 server rack intake temperature: 19.5°C. Secondary coolant cycle filter requires replacement on Friday. Scheduled HVAC technician.', N'Maintenance', 1, N'#8b5cf6'),
(2, 1, N'Weekly Office Floor Power Distribution Log', N'Checked 4th floor UPS units. Unit B battery health at 96%. All emergency backup switches calibrated for automated generator cutover test next week.', N'Urgent', 0, N'#ec4899'),
(3, 2, N'Cafeteria Plumbing & Water Filtration Check', N'Replaced dual carbon cartridge in water purification line. Clean water flow restored to 45 L/min. Safe water test parameters verified.', N'Checklist', 0, N'#3b82f6'),
(4, 3, N'Fire Suppression Sensor Network Verification', N'All 32 optical smoke sensors in Block B tested successfully via master telemetry panel. No telemetry packets dropped over RS-485 bus.', N'Daily Standup', 1, N'#10b981');

SET IDENTITY_INSERT dbo.daily_notes OFF;
GO

-- 3. Insert Project & Repository Cards (Feature 2)
SET IDENTITY_INSERT dbo.project_repos ON;

INSERT INTO dbo.project_repos (id, user_id, project_name, description, repo_url, live_demo_url, documentation_url, tech_stack, category, status)
VALUES
(1, 1, N'IoT-Smart-HVAC-Monitor', N'Real-time telemetry and automated climate control integration across 5 office blocks.', N'https://github.com/officeflow/smart-hvac-monitor', N'https://hvac-telemetry.corp.officeflow.com', N'https://docs.officeflow.com/hvac', N'Java 17, Spring Boot, MQTT, SSMS', N'IT Support', N'ACTIVE'),
(2, 1, N'Office-Access-Pass-Portal', N'Digital badge access management system, visitor approvals, and QR security validation.', N'https://github.com/officeflow/access-pass-portal', N'https://pass.officeflow.com', N'https://docs.officeflow.com/access', N'Spring Boot 3, SSMS (SQL Server), Tailwind CSS', N'Security', N'ACTIVE'),
(3, 2, N'Facility-Power-Telemetry', N'High voltage load balancing dashboard and generator automatic failover logger.', N'https://github.com/officeflow/facility-power-telemetry', N'https://power.officeflow.com', N'https://docs.officeflow.com/power', N'Java, Spring Data JPA, Chart.js', N'Facility', N'UNDER_REVIEW'),
(4, 3, N'Smart-Workstation-Booking', N'Floor plan seat reservation and conference room projector hardware diagnostic manager.', N'https://github.com/officeflow/workstation-desk-book', N'https://desk.officeflow.com', N'https://docs.officeflow.com/desk', N'React, Spring Boot, SSMS', N'Operations', N'COMPLETED');

SET IDENTITY_INSERT dbo.project_repos OFF;
GO

-- 4. Insert Daily Streaks (Feature 3)
INSERT INTO dbo.daily_streaks (user_id, log_date, task_summary, hours_logged, status, streak_count_snapshot, notes)
VALUES
(1, DATEADD(day, -4, CAST(GETDATE() AS DATE)), N'Floor 2 sensor calibration and server room check', 8.0, N'COMPLETED', 14, N'All logs green'),
(1, DATEADD(day, -3, CAST(GETDATE() AS DATE)), N'UPS load testing and battery diagnostics', 8.5, N'COMPLETED', 15, N'Zero downtime observed'),
(1, DATEADD(day, -2, CAST(GETDATE() AS DATE)), N'Emergency exit lighting fixture inspection', 7.5, N'COMPLETED', 16, N'Replaced 2 LED indicators'),
(1, DATEADD(day, -1, CAST(GETDATE() AS DATE)), N'Water pressure regulation and pipeline audit', 8.0, N'COMPLETED', 17, N'Routine check clear'),
(1, CAST(GETDATE() AS DATE), N'Standup meeting and Weekly task sheet distribution', 8.0, N'COMPLETED', 18, N'Distributed team excel sheet');
GO

-- 5. Insert Monthly Report (Feature 4)
SET IDENTITY_INSERT dbo.monthly_reports ON;

INSERT INTO dbo.monthly_reports (id, user_id, report_title, report_month, department, content_html, executive_summary, total_tasks_completed, maintenance_cost, status, reviewed_by)
VALUES
(1, 1, N'Comprehensive Facility Maintenance & Operations Report', N'October 2026', N'Facility Operations', 
N'<h2>1. Executive Summary</h2><p>The office infrastructure maintained an outstanding <b>99.94% facility uptime</b> across October 2026. A total of 142 routine and preventive maintenance tickets were closed ahead of target SLA.</p><h2>2. Key Systems Performance</h2><ul><li><b>HVAC Optimization:</b> Power usage decreased by 12.4% following automated cycle tuning.</li><li><b>IT Network & Server Room:</b> Backup generators successfully triggered 2 test cutovers without brownouts.</li><li><b>Safety Audits:</b> Fire safety & suppression compliance achieved 100% score on municipal inspection.</li></ul><h2>3. Budget Allocation</h2><p>Actual maintenance spend was $18,450 vs allocated budget of $22,000, achieving a 16.1% cost optimization.</p>',
N'Achieved 99.94% facility uptime and closed 142 tickets with 16.1% budget savings.', 142, 18450.00, N'APPROVED', N'Chief Operations Officer');

SET IDENTITY_INSERT dbo.monthly_reports OFF;
GO

-- 6. Insert Project Idea Proposals (Feature 5: Old Idea vs New Suggestion)
SET IDENTITY_INSERT dbo.idea_proposals ON;

INSERT INTO dbo.idea_proposals (id, user_id, project_repo_id, project_name, old_idea_description, new_suggestion_idea, benefits, estimated_impact, status, lead_feedback)
VALUES
(1, 2, 1, N'IoT-Smart-HVAC-Monitor',
N'Old Idea: Technicians manually walk around each office floor with digital thermometers every morning at 9:00 AM and enter numbers into a paper binder.',
N'New Suggestion: Mount low-power Zigbee/LoRaWAN temperature and humidity sensors on each ceiling grid that broadcast real-time metrics to our Spring Boot backend every 5 minutes with automated Telegram/Email alerts when thresholds exceed 24°C.',
N'Eliminates 3 hours of manual technician rounds per day, provides instant leak/overheating detection, and prevents server room hardware thermal throttling.',
N'HIGH', N'ACCEPTED', N'Approved by Team Lead Alex. Budget approved for 40 wireless sensors.'),
(2, 3, 2, N'Office-Access-Pass-Portal',
N'Old Idea: Physical laminated visitor badges written by security desk guards using Sharpie markers.',
N'New Suggestion: Self-service iPad kiosk with WebRTC webcam and instant QR code passes generated directly into visitor phone wallets.',
N'Zero reception queue bottlenecks, real-time fire evacuation headcount tracking, and enhanced building security compliance.',
N'CRITICAL', N'UNDER_REVIEW', N'Under review with Facilities & Security Director.');

SET IDENTITY_INSERT dbo.idea_proposals OFF;
GO

-- 7. Insert Monthly Performance Evaluation (Feature 6)
SET IDENTITY_INSERT dbo.performance_evaluations ON;

INSERT INTO dbo.performance_evaluations (id, user_id, evaluator_id, evaluation_month, maintenance_sla_score, uptime_reliability_score, task_completion_score, teamwork_initiative_score, overall_score, grade, strengths, areas_of_improvement, lead_comments, status)
VALUES
(1, 2, 1, N'October 2026', 96, 98, 94, 95, 95.75, N'A+',
N'Proactive emergency response during transformer surge. Outstanding initiative in drafting the wireless HVAC sensor modernization proposal.',
N'Continue mentoring junior apprentices on high-voltage 3-phase wiring safety protocols.',
N'Exceptional work throughout the entire maintenance cycle. Key pillar of the facility engineering team.', N'PUBLISHED'),
(2, 3, 1, N'October 2026', 92, 95, 90, 88, 91.25, N'A',
N'Rapid resolution of server rack network drops and fiber patch reliability.',
N'Improve documentation updates on repository readmes after code merges.',
N'Solid technical performance and high reliability during night maintenance shifts.', N'PUBLISHED');

SET IDENTITY_INSERT dbo.performance_evaluations OFF;
GO

-- 8. Insert Team Lead Excel Task Sheet (Feature 7)
SET IDENTITY_INSERT dbo.task_sheets ON;

INSERT INTO dbo.task_sheets (id, lead_id, sheet_title, sheet_description, target_date, is_active)
VALUES
(1, 1, N'Weekly Office Infrastructure & Preventive Maintenance Sheet', N'Active team spreadsheet: each member update assigned rows with daily work status, time spent, and blockers.', DATEADD(day, 3, CAST(GETDATE() AS DATE)), 1);

SET IDENTITY_INSERT dbo.task_sheets OFF;
GO

-- Insert Task Sheet Rows
SET IDENTITY_INSERT dbo.task_sheet_rows ON;

INSERT INTO dbo.task_sheet_rows (id, task_sheet_id, assigned_user_id, task_name, category, priority, assigned_member_name, member_work_update, time_spent_hours, status, last_updated_by)
VALUES
(1, 1, 2, N'Replace HEPA Air Filters in Executive Wing', N'HVAC Air Quality', N'HIGH', N'Sarah Connor', N'Completed 8 of 10 air handler units. Filter batch #B92 installed. Final 2 units scheduled for 3 PM.', 3.5, N'IN_PROGRESS', N'Sarah Connor'),
(2, 1, 3, N'Server Room Rack 4 Cable Tidying & Patch Panel Audit', N'IT Infrastructure', N'MEDIUM', N'David Chen', N'Finished patch panel labeling. Cat6A cables organized with velcro wraps. All links verified at 10 Gbps.', 2.0, N'COMPLETED', N'David Chen'),
(3, 1, 4, N'Test Emergency Evacuation Sirens & Strobe Horns', N'Safety & Compliance', N'URGENT', N'Maya Patel', N'Tested floor 1 through 3 horns. Floor 2 east strobe had loose wiring, tightened terminal screws and verified audio dB level.', 4.0, N'COMPLETED', N'Maya Patel'),
(4, 1, 2, N'Main Generator Fuel Reserve & Coolant Check', N'Power Backup', N'HIGH', N'Sarah Connor', N'Diesel tank capacity measured at 92%. Coolant glycol concentration within optimal 50/50 spec.', 1.5, N'COMPLETED', N'Sarah Connor'),
(5, 1, 3, N'Conference Room Crestron Display Firmware Upgrade', N'AV Maintenance', N'LOW', N'David Chen', N'Pending team member input. Waiting for conference room 402 to be vacated after board meeting.', 0.0, N'PENDING', N'David Chen');

SET IDENTITY_INSERT dbo.task_sheet_rows OFF;
GO

-- 9. Insert Email Access Grant (Feature 8)
SET IDENTITY_INSERT dbo.email_access_grants ON;

INSERT INTO dbo.email_access_grants (id, lead_id, user_id, granted_org_email, permissions, status, grant_notes)
VALUES
(1, 1, 2, N'sarah.connor@corp.officeflow.com', N'SEND_LEAVE,SEND_ANNOUNCEMENT,SEND_BROADCAST', N'ACTIVE', N'Granted full office organization broadcast & leave dispatch access by Team Lead Alex.'),
(2, 1, 3, N'david.chen@corp.officeflow.com', N'SEND_LEAVE,SEND_ANNOUNCEMENT', N'ACTIVE', N'Granted facility leave and maintenance alerts access.');

SET IDENTITY_INSERT dbo.email_access_grants OFF;
GO

-- 10. Insert Sample Organization Sent Emails
SET IDENTITY_INSERT dbo.org_email_logs ON;

INSERT INTO dbo.org_email_logs (id, sender_user_id, sender_org_email, recipient_type, recipient_emails, email_type, subject, email_body, sent_status)
VALUES
(1, 2, N'sarah.connor@corp.officeflow.com', N'ALL_ORGANIZATION', N'all-staff@corp.officeflow.com', N'MAINTENANCE_ALERT',
N'[MAINTENANCE NOTICE] Scheduled Water Main Valve Servicing Tonight 9 PM - 11 PM',
N'Dear Officeflow Team,' + CHAR(13) + CHAR(10) + CHAR(13) + CHAR(10) + N'Please note that the main domestic water line on Floors 2 and 3 will undergo routine valve seal replacement tonight between 21:00 and 23:00. Restroom water dispensers will be temporarily diverted. Thank you for your cooperation.' + CHAR(13) + CHAR(10) + CHAR(13) + CHAR(10) + N'Best regards,' + CHAR(13) + CHAR(10) + N'Sarah Connor' + CHAR(13) + CHAR(10) + N'Senior Systems Technician | Facility Operations', N'SENT'),
(2, 3, N'david.chen@corp.officeflow.com', N'TEAM_LEAD', N'alex.rivera@corp.officeflow.com, hr@corp.officeflow.com', N'LEAVE_REQUEST',
N'[LEAVE REQUEST] Planned Annual Leave - Nov 12 to Nov 15 (4 Days)',
N'Hi Alex & HR Team,' + CHAR(13) + CHAR(10) + CHAR(13) + CHAR(10) + N'I would like to apply for 4 days of annual leave from November 12th to November 15th, 2026. All server maintenance schedules for that week have been delegated to Sarah Connor. The backup rota is updated.' + CHAR(13) + CHAR(10) + CHAR(13) + CHAR(10) + N'Thank you,' + CHAR(13) + CHAR(10) + N'David Chen' + CHAR(13) + CHAR(10) + N'Network Maintenance Engineer', N'SENT');

SET IDENTITY_INSERT dbo.org_email_logs OFF;
GO
