/**
 * ============================================================================
 * OfficeFlow - API Client & Data Adapter
 * Connects to Spring Boot 3 Backend with SSMS (Microsoft SQL Server) (http://localhost:8080/api)
 * Dual-Mode: Automatically uses local persistent storage fallback if backend is offline.
 * ============================================================================
 */

const API_BASE_URL = 'http://localhost:8080/api';

// Initial Mock Seed Data matching database/seed_data.sql for SSMS
const DEFAULT_USERS = [
  { id: 1, name: 'Alex Rivera', email: 'lead@officeflow.com', role: 'TEAM_LEAD', department: 'Facility Operations', designation: 'Lead Facilities Architect', organizationEmail: 'alex.rivera@corp.officeflow.com', hasOrgEmailAccess: true, avatarUrl: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150' },
  { id: 2, name: 'Sarah Connor', email: 'sarah@officeflow.com', role: 'TEAM_MEMBER', department: 'Electrical & HVAC', designation: 'Senior Systems Technician', organizationEmail: 'sarah.connor@corp.officeflow.com', hasOrgEmailAccess: true, avatarUrl: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150' },
  { id: 3, name: 'David Chen', email: 'david@officeflow.com', role: 'TEAM_MEMBER', department: 'IT Network & Servers', designation: 'Network Maintenance Engineer', organizationEmail: 'david.chen@corp.officeflow.com', hasOrgEmailAccess: true, avatarUrl: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150' },
  { id: 4, name: 'Maya Patel', email: 'maya@officeflow.com', role: 'TEAM_MEMBER', department: 'Workplace Safety & Compliance', designation: 'Safety Operations Associate', organizationEmail: null, hasOrgEmailAccess: false, avatarUrl: 'https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150' }
];

const DEFAULT_NOTES = [
  { id: 1, userId: 1, title: 'Server Room HVAC Temperature Audit', content: 'Inspected Zone 3 server rack intake temperature: 19.5°C. Secondary coolant cycle filter requires replacement on Friday. Scheduled HVAC technician.', category: 'Maintenance', isPinned: true, colorTag: '#8b5cf6', updatedAt: '2026-10-30 14:30:00' },
  { id: 2, userId: 1, title: 'Weekly Office Floor Power Distribution Log', content: 'Checked 4th floor UPS units. Unit B battery health at 96%. All emergency backup switches calibrated for automated generator cutover test next week.', category: 'Urgent', isPinned: false, colorTag: '#ec4899', updatedAt: '2026-10-29 11:20:00' },
  { id: 3, userId: 1, title: 'Cafeteria Plumbing & Water Filtration Check', content: 'Replaced dual carbon cartridge in water purification line. Clean water flow restored to 45 L/min. Safe water test parameters verified.', category: 'Checklist', isPinned: false, colorTag: '#3b82f6', updatedAt: '2026-10-28 16:45:00' },
  { id: 4, userId: 1, title: 'Fire Suppression Sensor Network Verification', content: 'All 32 optical smoke sensors in Block B tested successfully via master telemetry panel. No telemetry packets dropped over RS-485 bus.', category: 'Daily Standup', isPinned: true, colorTag: '#10b981', updatedAt: '2026-10-27 09:15:00' }
];

const DEFAULT_PROJECTS = [
  { id: 1, projectName: 'IoT-Smart-HVAC-Monitor', description: 'Real-time telemetry and automated climate control integration across 5 office blocks.', repoUrl: 'https://github.com/officeflow/smart-hvac-monitor', liveDemoUrl: 'https://hvac-telemetry.corp.officeflow.com', documentationUrl: 'https://docs.officeflow.com/hvac', techStack: 'Java 17, Spring Boot, MQTT, SSMS', category: 'IT Support', status: 'ACTIVE' },
  { id: 2, projectName: 'Office-Access-Pass-Portal', description: 'Digital badge access management system, visitor approvals, and QR security validation.', repoUrl: 'https://github.com/officeflow/access-pass-portal', liveDemoUrl: 'https://pass.officeflow.com', documentationUrl: 'https://docs.officeflow.com/access', techStack: 'Spring Boot 3, SSMS (SQL Server), Tailwind CSS', category: 'Security', status: 'ACTIVE' },
  { id: 3, projectName: 'Facility-Power-Telemetry', description: 'High voltage load balancing dashboard and generator automatic failover logger.', repoUrl: 'https://github.com/officeflow/facility-power-telemetry', liveDemoUrl: 'https://power.officeflow.com', documentationUrl: 'https://docs.officeflow.com/power', techStack: 'Java, Spring Data JPA, SSMS, Chart.js', category: 'Facility', status: 'UNDER_REVIEW' },
  { id: 4, projectName: 'Smart-Workstation-Booking', description: 'Floor plan seat reservation and conference room projector hardware diagnostic manager.', repoUrl: 'https://github.com/officeflow/workstation-desk-book', liveDemoUrl: 'https://desk.officeflow.com', documentationUrl: 'https://docs.officeflow.com/desk', techStack: 'React, Spring Boot, SSMS', category: 'Operations', status: 'COMPLETED' }
];

const DEFAULT_STREAKS = [
  { id: 1, logDate: '2026-10-26', taskSummary: 'Floor 2 sensor calibration and server room check', hoursLogged: 8.0, status: 'COMPLETED', streakCountSnapshot: 14, notes: 'All green' },
  { id: 2, logDate: '2026-10-27', taskSummary: 'UPS load testing and battery diagnostics', hoursLogged: 8.5, status: 'COMPLETED', streakCountSnapshot: 15, notes: 'Zero downtime' },
  { id: 3, logDate: '2026-10-28', taskSummary: 'Emergency exit lighting fixture inspection', hoursLogged: 7.5, status: 'COMPLETED', streakCountSnapshot: 16, notes: 'Replaced 2 LED indicators' },
  { id: 4, logDate: '2026-10-29', taskSummary: 'Water pressure regulation and pipeline audit', hoursLogged: 8.0, status: 'COMPLETED', streakCountSnapshot: 17, notes: 'Routine check clear' },
  { id: 5, logDate: '2026-10-30', taskSummary: 'Standup meeting and Weekly task sheet distribution', hoursLogged: 8.0, status: 'COMPLETED', streakCountSnapshot: 18, notes: 'Distributed team excel sheet' }
];

const DEFAULT_REPORTS = [
  {
    id: 1,
    reportTitle: 'Comprehensive Facility Maintenance & Operations Report',
    reportMonth: 'October 2026',
    department: 'Facility Operations',
    contentHtml: '<h2>1. Executive Summary</h2><p>The office infrastructure maintained an outstanding <b>99.94% facility uptime</b> across October 2026. A total of 142 routine and preventive maintenance tickets were closed ahead of target SLA.</p><h2>2. Key Systems Performance</h2><ul><li><b>HVAC Optimization:</b> Power usage decreased by 12.4% following automated cycle tuning.</li><li><b>IT Network & Server Room:</b> Backup generators successfully triggered 2 test cutovers without brownouts.</li><li><b>Safety Audits:</b> Fire safety & suppression compliance achieved 100% score on municipal inspection.</li></ul><h2>3. Budget Allocation</h2><p>Actual maintenance spend was $18,450 vs allocated budget of $22,000, achieving a 16.1% cost optimization.</p>',
    executiveSummary: 'Achieved 99.94% facility uptime and closed 142 tickets with 16.1% budget savings.',
    totalTasksCompleted: 142,
    maintenanceCost: 18450.00,
    status: 'APPROVED',
    reviewedBy: 'Chief Operations Officer',
    createdAt: '2026-10-30'
  }
];

const DEFAULT_IDEAS = [
  {
    id: 1,
    projectName: 'IoT-Smart-HVAC-Monitor',
    oldIdeaDescription: 'Old Idea: Technicians manually walk around each office floor with digital thermometers every morning at 9:00 AM and enter numbers into a paper binder.',
    newSuggestionIdea: 'New Suggestion: Mount low-power Zigbee/LoRaWAN temperature and humidity sensors on each ceiling grid that broadcast real-time metrics to our Spring Boot backend every 5 minutes with automated Telegram/Email alerts when thresholds exceed 24°C.',
    benefits: 'Eliminates 3 hours of manual technician rounds per day, provides instant leak/overheating detection, and prevents server room hardware thermal throttling.',
    estimatedImpact: 'HIGH',
    status: 'ACCEPTED',
    leadFeedback: 'Approved by Team Lead Alex. Budget approved for 40 wireless sensors.',
    user: { name: 'Sarah Connor' },
    createdAt: '2026-10-25'
  },
  {
    id: 2,
    projectName: 'Office-Access-Pass-Portal',
    oldIdeaDescription: 'Old Idea: Physical laminated visitor badges written by security desk guards using Sharpie markers.',
    newSuggestionIdea: 'New Suggestion: Self-service iPad kiosk with WebRTC webcam and instant QR code passes generated directly into visitor phone wallets.',
    benefits: 'Zero reception queue bottlenecks, real-time fire evacuation headcount tracking, and enhanced building security compliance.',
    estimatedImpact: 'CRITICAL',
    status: 'UNDER_REVIEW',
    leadFeedback: 'Under review with Facilities & Security Director.',
    user: { name: 'David Chen' },
    createdAt: '2026-10-28'
  }
];

const DEFAULT_EVALUATIONS = [
  {
    id: 1,
    evaluationMonth: 'October 2026',
    user: { id: 2, name: 'Sarah Connor', designation: 'Senior Systems Technician', department: 'Electrical & HVAC' },
    maintenanceSlaScore: 96,
    uptimeReliabilityScore: 98,
    taskCompletionScore: 94,
    teamworkInitiativeScore: 95,
    overallScore: 95.75,
    grade: 'A+',
    strengths: 'Proactive emergency response during transformer surge. Outstanding initiative in drafting the wireless HVAC sensor proposal.',
    areasOfImprovement: 'Continue mentoring junior apprentices on high-voltage 3-phase wiring safety protocols.',
    leadComments: 'Exceptional work throughout the entire maintenance cycle. Key pillar of the facility engineering team.',
    status: 'PUBLISHED'
  },
  {
    id: 2,
    evaluationMonth: 'October 2026',
    user: { id: 3, name: 'David Chen', designation: 'Network Maintenance Engineer', department: 'IT Network & Servers' },
    maintenanceSlaScore: 92,
    uptimeReliabilityScore: 95,
    taskCompletionScore: 90,
    teamworkInitiativeScore: 88,
    overallScore: 91.25,
    grade: 'A',
    strengths: 'Rapid resolution of server rack network drops and fiber patch reliability.',
    areasOfImprovement: 'Improve documentation updates on repository readmes after code merges.',
    leadComments: 'Solid technical performance and high reliability during night maintenance shifts.',
    status: 'PUBLISHED'
  }
];

const DEFAULT_TASKSHEETS = [
  {
    id: 1,
    sheetTitle: 'Weekly Office Infrastructure & Preventive Maintenance Sheet',
    sheetDescription: 'Active team spreadsheet: each member update assigned rows with daily work status, time spent, and blockers.',
    targetDate: '2026-11-03',
    isActive: true,
    rows: [
      { id: 1, taskName: 'Replace HEPA Air Filters in Executive Wing', category: 'HVAC Air Quality', priority: 'HIGH', assignedMemberName: 'Sarah Connor', memberWorkUpdate: 'Completed 8 of 10 air handler units. Filter batch #B92 installed. Final 2 units scheduled for 3 PM.', timeSpentHours: 3.5, status: 'IN_PROGRESS' },
      { id: 2, taskName: 'Server Room Rack 4 Cable Tidying & Patch Panel Audit', category: 'IT Infrastructure', priority: 'MEDIUM', assignedMemberName: 'David Chen', memberWorkUpdate: 'Finished patch panel labeling. Cat6A cables organized with velcro wraps. All links verified at 10 Gbps.', timeSpentHours: 2.0, status: 'COMPLETED' },
      { id: 3, taskName: 'Test Emergency Evacuation Sirens & Strobe Horns', category: 'Safety & Compliance', priority: 'URGENT', assignedMemberName: 'Maya Patel', memberWorkUpdate: 'Tested floor 1 through 3 horns. Floor 2 east strobe had loose wiring, tightened terminal screws and verified audio dB level.', timeSpentHours: 4.0, status: 'COMPLETED' },
      { id: 4, taskName: 'Main Generator Fuel Reserve & Coolant Check', category: 'Power Backup', priority: 'HIGH', assignedMemberName: 'Sarah Connor', memberWorkUpdate: 'Diesel tank capacity measured at 92%. Coolant glycol concentration within optimal 50/50 spec.', timeSpentHours: 1.5, status: 'COMPLETED' },
      { id: 5, taskName: 'Conference Room Crestron Display Firmware Upgrade', category: 'AV Maintenance', priority: 'LOW', assignedMemberName: 'David Chen', memberWorkUpdate: 'Pending team member input. Waiting for conference room 402 to be vacated after board meeting.', timeSpentHours: 0.0, status: 'PENDING' }
    ]
  }
];

const DEFAULT_EMAIL_GRANTS = [
  { id: 1, user: { id: 2, name: 'Sarah Connor', email: 'sarah@officeflow.com' }, grantedOrgEmail: 'sarah.connor@corp.officeflow.com', permissions: 'SEND_LEAVE,SEND_ANNOUNCEMENT,SEND_BROADCAST', status: 'ACTIVE', grantNotes: 'Granted full office organization broadcast & leave dispatch access by Team Lead Alex.' },
  { id: 2, user: { id: 3, name: 'David Chen', email: 'david@officeflow.com' }, grantedOrgEmail: 'david.chen@corp.officeflow.com', permissions: 'SEND_LEAVE,SEND_ANNOUNCEMENT', status: 'ACTIVE', grantNotes: 'Granted facility leave and maintenance alerts access.' },
  { id: 3, user: { id: 4, name: 'Maya Patel', email: 'maya@officeflow.com' }, grantedOrgEmail: 'maya.patel@corp.officeflow.com', permissions: 'SEND_LEAVE', status: 'REVOKED', grantNotes: 'Access expired after compliance audit.' }
];

const DEFAULT_EMAIL_LOGS = [
  {
    id: 1,
    senderOrgEmail: 'sarah.connor@corp.officeflow.com',
    recipientType: 'ALL_ORGANIZATION',
    recipientEmails: 'all-staff@corp.officeflow.com',
    emailType: 'MAINTENANCE_ALERT',
    subject: '[MAINTENANCE NOTICE] Scheduled Water Main Valve Servicing Tonight 9 PM - 11 PM',
    emailBody: 'Dear Officeflow Team,\n\nPlease note that the main domestic water line on Floors 2 and 3 will undergo routine valve seal replacement tonight between 21:00 and 23:00. Restroom water dispensers will be temporarily diverted.\n\nBest regards,\nSarah Connor\nSenior Systems Technician | Facility Operations',
    sentStatus: 'SENT',
    sentAt: '2026-10-30 18:00:00'
  },
  {
    id: 2,
    senderOrgEmail: 'david.chen@corp.officeflow.com',
    recipientType: 'TEAM_LEAD',
    recipientEmails: 'alex.rivera@corp.officeflow.com, hr@corp.officeflow.com',
    emailType: 'LEAVE_REQUEST',
    subject: '[LEAVE REQUEST] Planned Annual Leave - Nov 12 to Nov 15 (4 Days)',
    emailBody: 'Hi Alex & HR Team,\n\nI would like to apply for 4 days of annual leave from November 12th to November 15th, 2026. All server maintenance schedules for that week have been delegated to Sarah Connor. The backup rota is updated.\n\nThank you,\nDavid Chen\nNetwork Maintenance Engineer',
    sentStatus: 'SENT',
    sentAt: '2026-10-29 09:30:00'
  }
];

// Helper: LocalStorage initialization
function initLocalStorage() {
  if (!localStorage.getItem('of_users')) localStorage.setItem('of_users', JSON.stringify(DEFAULT_USERS));
  if (!localStorage.getItem('of_notes')) localStorage.setItem('of_notes', JSON.stringify(DEFAULT_NOTES));
  if (!localStorage.getItem('of_projects')) localStorage.setItem('of_projects', JSON.stringify(DEFAULT_PROJECTS));
  if (!localStorage.getItem('of_streaks')) localStorage.setItem('of_streaks', JSON.stringify(DEFAULT_STREAKS));
  if (!localStorage.getItem('of_reports')) localStorage.setItem('of_reports', JSON.stringify(DEFAULT_REPORTS));
  if (!localStorage.getItem('of_ideas')) localStorage.setItem('of_ideas', JSON.stringify(DEFAULT_IDEAS));
  if (!localStorage.getItem('of_evaluations')) localStorage.setItem('of_evaluations', JSON.stringify(DEFAULT_EVALUATIONS));
  if (!localStorage.getItem('of_tasksheets')) localStorage.setItem('of_tasksheets', JSON.stringify(DEFAULT_TASKSHEETS));
  if (!localStorage.getItem('of_email_grants')) localStorage.setItem('of_email_grants', JSON.stringify(DEFAULT_EMAIL_GRANTS));
  if (!localStorage.getItem('of_email_logs')) localStorage.setItem('of_email_logs', JSON.stringify(DEFAULT_EMAIL_LOGS));
}

initLocalStorage();

let isBackendAvailable = false;
let lastCheckTime = 0;

// Updates the top navigation status badge
function updateBackendStatusBadge(isOnline) {
  const indicator = document.getElementById('backend-status-indicator');
  const text = document.getElementById('backend-status-text');
  if (!indicator || !text) return;

  if (isOnline) {
    indicator.className = 'w-2.5 h-2.5 rounded-full bg-emerald-500 animate-pulse';
    text.className = 'text-xs font-semibold text-emerald-700';
    text.textContent = '🟢 SSMS Backend Connected (port 8080)';
  } else {
    indicator.className = 'w-2.5 h-2.5 rounded-full bg-amber-400';
    text.className = 'text-xs font-semibold text-amber-700';
    text.textContent = '🟡 Offline Mode (Start Spring Boot on 8080)';
  }
}

// Live Connectivity Checker with cache TTL of 5 seconds
async function checkBackendConnection(forceRefresh = false) {
  const now = Date.now();
  if (!forceRefresh && (now - lastCheckTime) < 5000) {
    return isBackendAvailable;
  }
  lastCheckTime = now;

  try {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 1500);
    const res = await fetch(`${API_BASE_URL}/dashboard/stats`, {
      method: 'GET',
      headers: { 'Accept': 'application/json' },
      signal: controller.signal
    });
    clearTimeout(timeoutId);

    const wasOnline = isBackendAvailable;
    isBackendAvailable = res.ok;
    updateBackendStatusBadge(isBackendAvailable);

    if (isBackendAvailable && !wasOnline) {
      console.log('%c[OfficeFlow] Connected to Spring Boot Backend (SSMS SQL Server) on port 8080!', 'color: #10b981; font-weight: bold; font-size: 13px;');
      if (typeof showToast === 'function') {
        showToast('Connected to SSMS Spring Boot Backend (port 8080)', 'success');
      }
    }
    return isBackendAvailable;
  } catch (e) {
    isBackendAvailable = false;
    updateBackendStatusBadge(false);
    return false;
  }
}

// Initial check & auto-refresh interval
document.addEventListener('DOMContentLoaded', () => {
  checkBackendConnection(true);
  setInterval(() => checkBackendConnection(false), 8000);
});

// Global API Object
const OfficeFlowAPI = {
  // 1. Auth & Users
  async login(email, password) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/auth/login`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, password })
        });
        if (res.ok) return await res.json();
      } catch (err) { console.warn('Backend call failed, falling back to local data', err); }
    }
    // Fallback: Local match
    const users = JSON.parse(localStorage.getItem('of_users') || '[]');
    const user = users.find(u => u.email.toLowerCase() === email.toLowerCase()) || users[0];
    return {
      success: true,
      token: 'jwt_mock_' + Date.now(),
      userId: user.id,
      name: user.name,
      email: user.email,
      role: user.role,
      department: user.department,
      designation: user.designation,
      organizationEmail: user.organizationEmail,
      hasOrgEmailAccess: user.hasOrgEmailAccess
    };
  },

  async getAllUsers() {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/auth/users`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    return JSON.parse(localStorage.getItem('of_users') || '[]');
  },

  // 2. Feature 1: Daily Notes
  async getNotes(userId, search = '') {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const url = search ? `${API_BASE_URL}/notes/user/${userId}?search=${encodeURIComponent(search)}` : `${API_BASE_URL}/notes/user/${userId}`;
        const res = await fetch(url);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    let notes = JSON.parse(localStorage.getItem('of_notes') || '[]');
    if (search) {
      const q = search.toLowerCase();
      notes = notes.filter(n => n.title.toLowerCase().includes(q) || n.content.toLowerCase().includes(q));
    }
    return notes.sort((a, b) => (b.isPinned ? 1 : 0) - (a.isPinned ? 1 : 0));
  },

  async saveNote(note) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const isEdit = !!note.id;
        const res = await fetch(isEdit ? `${API_BASE_URL}/notes/${note.id}` : `${API_BASE_URL}/notes`, {
          method: isEdit ? 'PUT' : 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(note)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const notes = JSON.parse(localStorage.getItem('of_notes') || '[]');
    if (note.id) {
      const idx = notes.findIndex(n => n.id === Number(note.id));
      if (idx !== -1) {
        notes[idx] = { ...notes[idx], ...note, updatedAt: new Date().toISOString() };
        localStorage.setItem('of_notes', JSON.stringify(notes));
        return notes[idx];
      }
    }
    const newNote = { ...note, id: Date.now(), isPinned: !!note.isPinned, updatedAt: new Date().toISOString() };
    notes.unshift(newNote);
    localStorage.setItem('of_notes', JSON.stringify(notes));
    return newNote;
  },

  async togglePinNote(noteId) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/notes/${noteId}/pin`, { method: 'PATCH' });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const notes = JSON.parse(localStorage.getItem('of_notes') || '[]');
    const n = notes.find(x => x.id === Number(noteId));
    if (n) {
      n.isPinned = !n.isPinned;
      localStorage.setItem('of_notes', JSON.stringify(notes));
    }
    return n;
  },

  async deleteNote(noteId) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        await fetch(`${API_BASE_URL}/notes/${noteId}`, { method: 'DELETE' });
      } catch (e) {}
    }
    let notes = JSON.parse(localStorage.getItem('of_notes') || '[]');
    notes = notes.filter(x => x.id !== Number(noteId));
    localStorage.setItem('of_notes', JSON.stringify(notes));
    return true;
  },

  // 3. Feature 2: Project & Repo Cards
  async getProjects(category = '') {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const url = category ? `${API_BASE_URL}/projects?category=${encodeURIComponent(category)}` : `${API_BASE_URL}/projects`;
        const res = await fetch(url);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    let projects = JSON.parse(localStorage.getItem('of_projects') || '[]');
    if (category) projects = projects.filter(p => p.category === category);
    return projects;
  },

  async saveProject(proj) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const isEdit = !!proj.id;
        const res = await fetch(isEdit ? `${API_BASE_URL}/projects/${proj.id}` : `${API_BASE_URL}/projects`, {
          method: isEdit ? 'PUT' : 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(proj)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const projects = JSON.parse(localStorage.getItem('of_projects') || '[]');
    if (proj.id) {
      const idx = projects.findIndex(p => p.id === Number(proj.id));
      if (idx !== -1) {
        projects[idx] = { ...projects[idx], ...proj };
        localStorage.setItem('of_projects', JSON.stringify(projects));
        return projects[idx];
      }
    }
    const newProj = { ...proj, id: Date.now() };
    projects.unshift(newProj);
    localStorage.setItem('of_projects', JSON.stringify(projects));
    return newProj;
  },

  async deleteProject(id) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        await fetch(`${API_BASE_URL}/projects/${id}`, { method: 'DELETE' });
      } catch (e) {}
    }
    let projects = JSON.parse(localStorage.getItem('of_projects') || '[]');
    projects = projects.filter(p => p.id !== Number(id));
    localStorage.setItem('of_projects', JSON.stringify(projects));
    return true;
  },

  // 4. Feature 3: Streak Tracker
  async getStreakSummary(userId) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/streaks/user/${userId}/summary`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const logs = JSON.parse(localStorage.getItem('of_streaks') || '[]');
    return {
      currentStreakDays: 18,
      totalDaysLogged: logs.length + 13,
      currentBadge: 'Gold Fortress Guardian (14+ Days)',
      nextMilestoneDays: 21,
      daysUntilNextMilestone: 3,
      recentLogs: logs
    };
  },

  async recordStreak(streakData) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/streaks`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(streakData)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const logs = JSON.parse(localStorage.getItem('of_streaks') || '[]');
    const newLog = {
      id: Date.now(),
      logDate: streakData.logDate || new Date().toISOString().split('T')[0],
      taskSummary: streakData.taskSummary,
      hoursLogged: Number(streakData.hoursLogged || 8.0),
      status: 'COMPLETED',
      notes: streakData.notes,
      streakCountSnapshot: 19
    };
    logs.unshift(newLog);
    localStorage.setItem('of_streaks', JSON.stringify(logs));
    return newLog;
  },

  // 5. Feature 4: Monthly Report (Word-like document)
  async getReports() {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/reports`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    return JSON.parse(localStorage.getItem('of_reports') || '[]');
  },

  async saveReport(report) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const isEdit = !!report.id;
        const res = await fetch(isEdit ? `${API_BASE_URL}/reports/${report.id}` : `${API_BASE_URL}/reports`, {
          method: isEdit ? 'PUT' : 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(report)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const reports = JSON.parse(localStorage.getItem('of_reports') || '[]');
    if (report.id) {
      const idx = reports.findIndex(r => r.id === Number(report.id));
      if (idx !== -1) {
        reports[idx] = { ...reports[idx], ...report, updatedAt: new Date().toISOString() };
        localStorage.setItem('of_reports', JSON.stringify(reports));
        return reports[idx];
      }
    }
    const newReport = { ...report, id: Date.now(), createdAt: new Date().toISOString() };
    reports.unshift(newReport);
    localStorage.setItem('of_reports', JSON.stringify(reports));
    return newReport;
  },

  // 6. Feature 5: Idea Proposals (Old Idea vs New Suggestion)
  async getIdeas(search = '') {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const url = search ? `${API_BASE_URL}/ideas?search=${encodeURIComponent(search)}` : `${API_BASE_URL}/ideas`;
        const res = await fetch(url);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    let ideas = JSON.parse(localStorage.getItem('of_ideas') || '[]');
    if (search) {
      const q = search.toLowerCase();
      ideas = ideas.filter(i => 
        i.projectName.toLowerCase().includes(q) ||
        i.oldIdeaDescription.toLowerCase().includes(q) ||
        i.newSuggestionIdea.toLowerCase().includes(q)
      );
    }
    return ideas;
  },

  async saveIdea(idea) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/ideas`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(idea)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const ideas = JSON.parse(localStorage.getItem('of_ideas') || '[]');
    const newIdea = {
      ...idea,
      id: Date.now(),
      status: 'PROPOSED',
      createdAt: new Date().toISOString().split('T')[0],
      user: { name: 'Active User' }
    };
    ideas.unshift(newIdea);
    localStorage.setItem('of_ideas', JSON.stringify(ideas));
    return newIdea;
  },

  async reviewIdea(id, status, feedback) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/ideas/${id}/review`, {
          method: 'PATCH',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ status, leadFeedback: feedback })
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const ideas = JSON.parse(localStorage.getItem('of_ideas') || '[]');
    const item = ideas.find(i => i.id === Number(id));
    if (item) {
      item.status = status;
      item.leadFeedback = feedback;
      localStorage.setItem('of_ideas', JSON.stringify(ideas));
    }
    return item;
  },

  // 7. Feature 6: Monthly Performance Evaluations
  async getEvaluations() {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/performance`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    return JSON.parse(localStorage.getItem('of_evaluations') || '[]');
  },

  async saveEvaluation(evalData) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/performance`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(evalData)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const evals = JSON.parse(localStorage.getItem('of_evaluations') || '[]');
    const overall = ((evalData.maintenanceSlaScore * 0.35) + 
                     (evalData.uptimeReliabilityScore * 0.25) + 
                     (evalData.taskCompletionScore * 0.25) + 
                     (evalData.teamworkInitiativeScore * 0.15)).toFixed(2);
    let grade = 'B';
    if (overall >= 95) grade = 'A+';
    else if (overall >= 85) grade = 'A';
    else if (overall >= 75) grade = 'B';
    else if (overall >= 60) grade = 'C';
    else grade = 'D';

    const users = JSON.parse(localStorage.getItem('of_users') || '[]');
    const targetUser = users.find(u => u.id === Number(evalData.userId)) || { name: 'Team Member' };

    const newEval = {
      ...evalData,
      id: Date.now(),
      overallScore: Number(overall),
      grade,
      user: targetUser,
      status: 'PUBLISHED'
    };
    evals.unshift(newEval);
    localStorage.setItem('of_evaluations', JSON.stringify(evals));
    return newEval;
  },

  // 8. Feature 7: Team Lead Excel Sheet & Rows
  async getTaskSheets() {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/tasksheets`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    return JSON.parse(localStorage.getItem('of_tasksheets') || '[]');
  },

  async saveTaskSheet(sheetData) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/tasksheets`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(sheetData)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const sheets = JSON.parse(localStorage.getItem('of_tasksheets') || '[]');
    const newSheet = {
      id: Date.now(),
      sheetTitle: sheetData.sheetTitle,
      sheetDescription: sheetData.sheetDescription,
      targetDate: sheetData.targetDate,
      isActive: true,
      rows: (sheetData.rows || []).map((r, i) => ({
        ...r,
        id: Date.now() + i,
        memberWorkUpdate: 'Assigned by Team Lead. Awaiting member update.',
        status: r.status || 'PENDING'
      }))
    };
    sheets.unshift(newSheet);
    localStorage.setItem('of_tasksheets', JSON.stringify(sheets));
    return newSheet;
  },

  async updateTaskRow(rowId, updateData) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/tasksheets/rows/${rowId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(updateData)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const sheets = JSON.parse(localStorage.getItem('of_tasksheets') || '[]');
    for (const sheet of sheets) {
      const row = sheet.rows.find(r => r.id === Number(rowId));
      if (row) {
        if (updateData.memberWorkUpdate !== undefined) row.memberWorkUpdate = updateData.memberWorkUpdate;
        if (updateData.timeSpentHours !== undefined) row.timeSpentHours = Number(updateData.timeSpentHours);
        if (updateData.status !== undefined) row.status = updateData.status;
        if (updateData.lastUpdatedBy !== undefined) row.lastUpdatedBy = updateData.lastUpdatedBy;
        localStorage.setItem('of_tasksheets', JSON.stringify(sheets));
        return row;
      }
    }
    return null;
  },

  // 9. Feature 8: Organization Email Permissions & Dispatch
  async getEmailGrants() {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/emails/grants`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    return JSON.parse(localStorage.getItem('of_email_grants') || '[]');
  },

  async grantEmailAccess(grantReq) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/emails/grants`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(grantReq)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const grants = JSON.parse(localStorage.getItem('of_email_grants') || '[]');
    const users = JSON.parse(localStorage.getItem('of_users') || '[]');
    const user = users.find(u => u.id === Number(grantReq.userId));
    if (user) {
      user.hasOrgEmailAccess = true;
      user.organizationEmail = grantReq.grantedOrgEmail;
      localStorage.setItem('of_users', JSON.stringify(users));
    }
    const newGrant = {
      id: Date.now(),
      user: user || { id: grantReq.userId, name: 'Team Member', email: grantReq.grantedOrgEmail },
      grantedOrgEmail: grantReq.grantedOrgEmail,
      permissions: grantReq.permissions || 'SEND_LEAVE,SEND_ANNOUNCEMENT,SEND_BROADCAST',
      status: 'ACTIVE',
      grantNotes: grantReq.grantNotes || 'Granted by Team Lead'
    };
    grants.unshift(newGrant);
    localStorage.setItem('of_email_grants', JSON.stringify(grants));
    return newGrant;
  },

  async revokeEmailAccess(grantId) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        await fetch(`${API_BASE_URL}/emails/grants/${grantId}/revoke`, { method: 'PATCH' });
      } catch (e) {}
    }
    const grants = JSON.parse(localStorage.getItem('of_email_grants') || '[]');
    const g = grants.find(x => x.id === Number(grantId));
    if (g) {
      g.status = 'REVOKED';
      const users = JSON.parse(localStorage.getItem('of_users') || '[]');
      const u = users.find(x => x.id === g.user.id);
      if (u) u.hasOrgEmailAccess = false;
      localStorage.setItem('of_users', JSON.stringify(users));
      localStorage.setItem('of_email_grants', JSON.stringify(grants));
    }
    return true;
  },

  async sendOrgEmail(emailReq) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/emails/send`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(emailReq)
        });
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const logs = JSON.parse(localStorage.getItem('of_email_logs') || '[]');
    const users = JSON.parse(localStorage.getItem('of_users') || '[]');
    const sender = users.find(u => u.id === Number(emailReq.senderUserId)) || users[0];

    const newLog = {
      id: Date.now(),
      senderOrgEmail: sender.organizationEmail || 'user@corp.officeflow.com',
      recipientType: emailReq.recipientType,
      recipientEmails: emailReq.recipientEmails,
      emailType: emailReq.emailType,
      subject: emailReq.subject,
      emailBody: emailReq.emailBody,
      sentStatus: 'SENT',
      sentAt: new Date().toISOString().replace('T', ' ').substring(0, 19)
    };
    logs.unshift(newLog);
    localStorage.setItem('of_email_logs', JSON.stringify(logs));
    return newLog;
  },

  async getEmailLogs() {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/emails/logs`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    return JSON.parse(localStorage.getItem('of_email_logs') || '[]');
  },

  // 10. Dashboard Stats
  async getDashboardStats(userId) {
    const online = await checkBackendConnection();
    if (online) {
      try {
        const res = await fetch(`${API_BASE_URL}/dashboard/stats?userId=${userId || 1}`);
        if (res.ok) return await res.json();
      } catch (e) {}
    }
    const notes = JSON.parse(localStorage.getItem('of_notes') || '[]');
    const projects = JSON.parse(localStorage.getItem('of_projects') || '[]');
    const reports = JSON.parse(localStorage.getItem('of_reports') || '[]');
    const ideas = JSON.parse(localStorage.getItem('of_ideas') || '[]');
    const sheets = JSON.parse(localStorage.getItem('of_tasksheets') || '[]');
    const emails = JSON.parse(localStorage.getItem('of_email_logs') || '[]');

    let pendingCount = 0;
    sheets.forEach(s => {
      (s.rows || []).forEach(r => {
        if (r.status === 'PENDING') pendingCount++;
      });
    });

    return {
      totalNotes: notes.length,
      totalProjects: projects.length,
      currentStreakDays: 18,
      totalReports: reports.length,
      totalIdeaProposals: ideas.length,
      teamAverageScore: 93.5,
      activeTaskSheets: sheets.filter(s => s.isActive).length,
      pendingExcelTasks: pendingCount,
      orgEmailsDispatched: emails.length,
      hasOrgEmailAccess: true,
      userRole: 'TEAM_LEAD'
    };
  }
};

window.OfficeFlowAPI = OfficeFlowAPI;
