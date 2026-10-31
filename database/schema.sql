-- ==========================================================
-- OfficeFlow - Office Task & Maintenance Management System
-- Database Schema for Microsoft SQL Server / SSMS (T-SQL)
-- ==========================================================

-- 1. Create Database if it doesn't already exist
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'officeflow_db')
BEGIN
    CREATE DATABASE officeflow_db;
END
GO

USE officeflow_db;
GO

-- 2. Drop existing foreign keys and tables if refreshing schema (safe recreation)
IF OBJECT_ID(N'dbo.org_email_logs', N'U') IS NOT NULL DROP TABLE dbo.org_email_logs;
IF OBJECT_ID(N'dbo.email_access_grants', N'U') IS NOT NULL DROP TABLE dbo.email_access_grants;
IF OBJECT_ID(N'dbo.task_sheet_rows', N'U') IS NOT NULL DROP TABLE dbo.task_sheet_rows;
IF OBJECT_ID(N'dbo.task_sheets', N'U') IS NOT NULL DROP TABLE dbo.task_sheets;
IF OBJECT_ID(N'dbo.performance_evaluations', N'U') IS NOT NULL DROP TABLE dbo.performance_evaluations;
IF OBJECT_ID(N'dbo.idea_proposals', N'U') IS NOT NULL DROP TABLE dbo.idea_proposals;
IF OBJECT_ID(N'dbo.monthly_reports', N'U') IS NOT NULL DROP TABLE dbo.monthly_reports;
IF OBJECT_ID(N'dbo.daily_streaks', N'U') IS NOT NULL DROP TABLE dbo.daily_streaks;
IF OBJECT_ID(N'dbo.project_repos', N'U') IS NOT NULL DROP TABLE dbo.project_repos;
IF OBJECT_ID(N'dbo.daily_notes', N'U') IS NOT NULL DROP TABLE dbo.daily_notes;
IF OBJECT_ID(N'dbo.users', N'U') IS NOT NULL DROP TABLE dbo.users;
GO

-- 1. Users Table (Authentication & Team Hierarchy)
CREATE TABLE dbo.users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(30) NOT NULL DEFAULT 'TEAM_MEMBER', -- TEAM_LEAD, TEAM_MEMBER, ADMIN
    department NVARCHAR(100) DEFAULT 'Maintenance & Facility',
    designation NVARCHAR(100) DEFAULT 'Operations Specialist',
    organization_email NVARCHAR(150) NULL,
    has_org_email_access BIT DEFAULT 0,
    avatar_url NVARCHAR(255) NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME()
);
GO

-- 2. Daily Notes Table (Feature 1: Daily Notepad inside App)
CREATE TABLE dbo.daily_notes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title NVARCHAR(200) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    category NVARCHAR(50) DEFAULT 'General', -- General, Maintenance, Daily Standup, Urgent, Checklist
    is_pinned BIT DEFAULT 0,
    color_tag NVARCHAR(30) DEFAULT '#8b5cf6',
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_note_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO

-- 3. Project & Repository Cards Table (Feature 2: Link storing & card preview)
CREATE TABLE dbo.project_repos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    project_name NVARCHAR(150) NOT NULL,
    description NVARCHAR(MAX) NULL,
    repo_url NVARCHAR(500) NOT NULL,
    live_demo_url NVARCHAR(500) NULL,
    documentation_url NVARCHAR(500) NULL,
    tech_stack NVARCHAR(255) DEFAULT 'Java 17, Spring Boot, SSMS (SQL Server)',
    category NVARCHAR(50) DEFAULT 'Office Maintenance', -- IT Support, Facility, Security, Operations
    status NVARCHAR(30) DEFAULT 'ACTIVE', -- ACTIVE, UNDER_REVIEW, COMPLETED, ARCHIVED
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_repo_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO

-- 4. Daily Streak Table (Feature 3: Managing daily maintenance updates & streak)
CREATE TABLE dbo.daily_streaks (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    log_date DATE NOT NULL,
    task_summary NVARCHAR(255) NOT NULL,
    hours_logged DECIMAL(4,2) DEFAULT 8.0,
    status NVARCHAR(30) DEFAULT 'COMPLETED', -- COMPLETED, IN_PROGRESS, MISSED
    streak_count_snapshot INT DEFAULT 1,
    notes NVARCHAR(MAX) NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT uq_user_log_date UNIQUE (user_id, log_date),
    CONSTRAINT fk_streak_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO

-- 5. Monthly Reports Table (Feature 4: Word-like document creator)
CREATE TABLE dbo.monthly_reports (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    report_title NVARCHAR(200) NOT NULL,
    report_month NVARCHAR(20) NOT NULL, -- e.g. "October 2026"
    department NVARCHAR(100) DEFAULT 'Facility & IT Maintenance',
    content_html NVARCHAR(MAX) NOT NULL, -- Rich formatted document text
    executive_summary NVARCHAR(MAX) NULL,
    total_tasks_completed INT DEFAULT 0,
    maintenance_cost DECIMAL(12,2) DEFAULT 0.00,
    status NVARCHAR(30) DEFAULT 'DRAFT', -- DRAFT, SUBMITTED, APPROVED
    reviewed_by NVARCHAR(100) NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_report_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO

-- 6. Project Idea Proposal & Comparison Table (Feature 5: Old idea vs New suggestion)
CREATE TABLE dbo.idea_proposals (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    project_repo_id BIGINT NULL,
    project_name NVARCHAR(150) NOT NULL,
    old_idea_description NVARCHAR(MAX) NOT NULL,
    new_suggestion_idea NVARCHAR(MAX) NOT NULL,
    benefits NVARCHAR(MAX) NULL,
    estimated_impact NVARCHAR(50) DEFAULT 'HIGH', -- LOW, MEDIUM, HIGH, CRITICAL
    status NVARCHAR(30) DEFAULT 'PROPOSED', -- PROPOSED, UNDER_REVIEW, ACCEPTED, REJECTED
    lead_feedback NVARCHAR(MAX) NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_idea_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE,
    CONSTRAINT fk_idea_project FOREIGN KEY (project_repo_id) REFERENCES dbo.project_repos(id) ON DELETE SET NULL
);
GO

-- 7. Monthly Performance Evaluations Table (Feature 6: Performance Evaluation of month)
CREATE TABLE dbo.performance_evaluations (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    evaluator_id BIGINT NOT NULL,
    evaluation_month NVARCHAR(20) NOT NULL,
    maintenance_sla_score INT DEFAULT 90, -- 1-100
    uptime_reliability_score INT DEFAULT 95, -- 1-100
    task_completion_score INT DEFAULT 88, -- 1-100
    teamwork_initiative_score INT DEFAULT 92, -- 1-100
    overall_score DECIMAL(5,2) NOT NULL,
    grade NVARCHAR(10) NOT NULL, -- A+, A, B, C, D
    strengths NVARCHAR(MAX) NULL,
    areas_of_improvement NVARCHAR(MAX) NULL,
    lead_comments NVARCHAR(MAX) NULL,
    status NVARCHAR(30) DEFAULT 'PUBLISHED', -- DRAFT, PUBLISHED
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_eval_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE,
    CONSTRAINT fk_eval_evaluator FOREIGN KEY (evaluator_id) REFERENCES dbo.users(id) ON DELETE NO ACTION
);
GO

-- 8. Team Excel Task Sheets (Feature 7: Team lead creates excel and sends to team)
CREATE TABLE dbo.task_sheets (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    lead_id BIGINT NOT NULL,
    sheet_title NVARCHAR(200) NOT NULL,
    sheet_description NVARCHAR(MAX) NULL,
    target_date DATE NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_sheet_lead FOREIGN KEY (lead_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO

-- 9. Task Sheet Rows (Interactive Excel rows updated by team members)
CREATE TABLE dbo.task_sheet_rows (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    task_sheet_id BIGINT NOT NULL,
    assigned_user_id BIGINT NULL,
    task_name NVARCHAR(200) NOT NULL,
    category NVARCHAR(100) DEFAULT 'Maintenance',
    priority NVARCHAR(20) DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, URGENT
    assigned_member_name NVARCHAR(100) NULL,
    member_work_update NVARCHAR(MAX) NULL, -- What member fills in
    time_spent_hours DECIMAL(4,2) DEFAULT 0.0,
    status NVARCHAR(30) DEFAULT 'PENDING', -- PENDING, IN_PROGRESS, COMPLETED, BLOCKED
    last_updated_by NVARCHAR(100) NULL,
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_row_sheet FOREIGN KEY (task_sheet_id) REFERENCES dbo.task_sheets(id) ON DELETE CASCADE,
    CONSTRAINT fk_row_user FOREIGN KEY (assigned_user_id) REFERENCES dbo.users(id) ON DELETE SET NULL
);
GO

-- 10. Organization Email Access Permissions (Feature 8: Lead grants email access)
CREATE TABLE dbo.email_access_grants (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    lead_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    granted_org_email NVARCHAR(150) NOT NULL,
    permissions NVARCHAR(255) DEFAULT 'SEND_LEAVE,SEND_ANNOUNCEMENT,SEND_BROADCAST',
    status NVARCHAR(30) DEFAULT 'ACTIVE', -- ACTIVE, REVOKED, EXPIRED
    grant_notes NVARCHAR(255) NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_emailgrant_lead FOREIGN KEY (lead_id) REFERENCES dbo.users(id) ON DELETE NO ACTION,
    CONSTRAINT fk_emailgrant_user FOREIGN KEY (user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO

-- 11. Organization Email Logs (Sent emails record)
CREATE TABLE dbo.org_email_logs (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    sender_user_id BIGINT NOT NULL,
    sender_org_email NVARCHAR(150) NOT NULL,
    recipient_type NVARCHAR(50) NOT NULL, -- ALL_ORGANIZATION, MAINTENANCE_TEAM, TEAM_LEAD, HR
    recipient_emails NVARCHAR(MAX) NOT NULL,
    email_type NVARCHAR(50) NOT NULL, -- LEAVE_REQUEST, MAINTENANCE_ALERT, DAILY_STATUS, GENERAL_BROADCAST
    subject NVARCHAR(255) NOT NULL,
    email_body NVARCHAR(MAX) NOT NULL,
    sent_status NVARCHAR(30) DEFAULT 'SENT', -- SENT, QUEUED, FAILED
    sent_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT fk_emaillog_sender FOREIGN KEY (sender_user_id) REFERENCES dbo.users(id) ON DELETE CASCADE
);
GO
