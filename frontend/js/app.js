/**
 * ============================================================================
 * OfficeFlow - Main Application Controller
 * Handles Navigation, State Management, and Interactivity for All 8 Modules
 * Database: SSMS (Microsoft SQL Server)
 * ============================================================================
 */

// Application State
const AppState = {
  currentUser: {
    id: 1,
    name: 'Alex Rivera',
    email: 'lead@officeflow.com',
    role: 'TEAM_LEAD',
    department: 'Facility Operations',
    designation: 'Lead Facilities Architect',
    organizationEmail: 'alex.rivera@corp.officeflow.com',
    hasOrgEmailAccess: true,
    avatarUrl: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150'
  },
  activeTab: 'dashboard',
  backendConnected: false
};

// UI Notification Toast
function showToast(title, message = '', type = 'success') {
  const container = document.getElementById('toast-container');
  if (!container) return;

  const toast = document.createElement('div');
  const bgColors = {
    success: 'bg-emerald-600 text-white',
    error: 'bg-rose-600 text-white',
    info: 'bg-purple-700 text-white'
  };

  toast.className = `toast p-4 rounded-xl shadow-xl flex items-start gap-3 min-w-[320px] ${bgColors[type] || bgColors.success}`;
  toast.innerHTML = `
    <div class="p-1 bg-white/20 rounded-lg">
      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
    </div>
    <div class="flex-1">
      <h4 class="font-bold text-sm leading-tight">${title}</h4>
      ${message ? `<p class="text-xs text-white/90 mt-0.5">${message}</p>` : ''}
    </div>
    <button onclick="this.parentElement.remove()" class="text-white/60 hover:text-white">&times;</button>
  `;

  container.appendChild(toast);
  requestAnimationFrame(() => toast.classList.add('show'));

  setTimeout(() => {
    toast.classList.remove('show');
    setTimeout(() => toast.remove(), 300);
  }, 4000);
}

// Check Backend Connection Status Indicator
async function updateBackendStatus() {
  const indicator = document.getElementById('backend-status-indicator');
  const text = document.getElementById('backend-status-text');
  try {
    const res = await fetch('http://localhost:8080/api/dashboard/stats', { method: 'GET' });
    AppState.backendConnected = res.ok;
  } catch (e) {
    AppState.backendConnected = false;
  }

  if (indicator && text) {
    if (AppState.backendConnected) {
      indicator.className = 'w-2.5 h-2.5 rounded-full bg-emerald-500 animate-pulse';
      text.innerText = 'Spring Boot & SSMS Connected';
      text.className = 'text-xs font-semibold text-emerald-700';
    } else {
      indicator.className = 'w-2.5 h-2.5 rounded-full bg-amber-400';
      text.innerText = 'Local Storage Mode (Port 8080 Offline)';
      text.className = 'text-xs font-medium text-amber-700';
    }
  }
}

// Navigation & Tab Switching
function switchTab(tabId) {
  AppState.activeTab = tabId;

  document.querySelectorAll('.nav-item').forEach(el => {
    const target = el.getAttribute('data-tab');
    if (target === tabId) {
      el.className = 'nav-item w-full flex items-center gap-3 px-3.5 py-2.5 rounded-xl bg-purple-100 text-purple-900 font-semibold transition-all shadow-sm';
    } else {
      el.className = 'nav-item w-full flex items-center gap-3 px-3.5 py-2.5 rounded-xl text-slate-600 hover:text-purple-700 hover:bg-purple-50 transition-all font-medium';
    }
  });

  document.querySelectorAll('.tab-content').forEach(section => {
    if (section.id === `tab-${tabId}`) {
      section.classList.remove('hidden');
    } else {
      section.classList.add('hidden');
    }
  });

  switch (tabId) {
    case 'dashboard': loadDashboard(); break;
    case 'notes': loadNotes(); break;
    case 'repos': loadProjects(); break;
    case 'streaks': loadStreaks(); break;
    case 'reports': loadReports(); break;
    case 'ideas': loadIdeas(); break;
    case 'performance': loadPerformance(); break;
    case 'tasksheets': loadTaskSheets(); break;
    case 'emails': loadEmails(); break;
  }
}

// Role Switcher
function switchUserRole(targetRole) {
  if (targetRole === 'TEAM_LEAD') {
    AppState.currentUser = {
      id: 1,
      name: 'Alex Rivera',
      email: 'lead@officeflow.com',
      role: 'TEAM_LEAD',
      department: 'Facility Operations',
      designation: 'Lead Facilities Architect',
      organizationEmail: 'alex.rivera@corp.officeflow.com',
      hasOrgEmailAccess: true,
      avatarUrl: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150'
    };
    showToast('Role Switched', 'Logged in as Alex Rivera (Team Lead)', 'info');
  } else {
    AppState.currentUser = {
      id: 2,
      name: 'Sarah Connor',
      email: 'sarah@officeflow.com',
      role: 'TEAM_MEMBER',
      department: 'Electrical & HVAC',
      designation: 'Senior Systems Technician',
      organizationEmail: 'sarah.connor@corp.officeflow.com',
      hasOrgEmailAccess: true,
      avatarUrl: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150'
    };
    showToast('Role Switched', 'Logged in as Sarah Connor (Team Member)', 'info');
  }

  updateUserHeaderDisplay();
  switchTab(AppState.activeTab);
}

function updateUserHeaderDisplay() {
  const nameEl = document.getElementById('user-display-name');
  const roleBadge = document.getElementById('user-role-badge');
  const emailEl = document.getElementById('user-display-email');
  const orgEmailEl = document.getElementById('user-org-email-display');
  const avatarEl = document.getElementById('user-avatar');

  if (nameEl) nameEl.innerText = AppState.currentUser.name;
  if (roleBadge) {
    roleBadge.innerText = AppState.currentUser.role === 'TEAM_LEAD' ? 'Team Lead' : 'Team Member';
    roleBadge.className = AppState.currentUser.role === 'TEAM_LEAD'
      ? 'text-xs font-bold px-2 py-0.5 rounded-full bg-purple-100 text-purple-700 border border-purple-200'
      : 'text-xs font-bold px-2 py-0.5 rounded-full bg-blue-100 text-blue-700 border border-blue-200';
  }
  if (emailEl) emailEl.innerText = AppState.currentUser.email;
  if (orgEmailEl) orgEmailEl.innerText = AppState.currentUser.organizationEmail || 'No Corp Email';
  if (avatarEl) avatarEl.src = AppState.currentUser.avatarUrl;
}

// 0. Dashboard
async function loadDashboard() {
  const stats = await OfficeFlowAPI.getDashboardStats(AppState.currentUser.id);
  document.getElementById('dash-stat-notes').innerText = stats.totalNotes;
  document.getElementById('dash-stat-projects').innerText = stats.totalProjects;
  document.getElementById('dash-stat-streak').innerText = `${stats.currentStreakDays} Days 🔥`;
  document.getElementById('dash-stat-reports').innerText = stats.totalReports;
  document.getElementById('dash-stat-ideas').innerText = stats.totalIdeaProposals;
  document.getElementById('dash-stat-performance').innerText = `${stats.teamAverageScore}%`;
  document.getElementById('dash-stat-tasksheets').innerText = stats.activeTaskSheets;
  document.getElementById('dash-stat-pending-tasks').innerText = stats.pendingExcelTasks;
  document.getElementById('dash-stat-emails').innerText = stats.orgEmailsDispatched;
}

// 1. Daily Notes
async function loadNotes() {
  const search = document.getElementById('notes-search-input')?.value || '';
  const notes = await OfficeFlowAPI.getNotes(AppState.currentUser.id, search);
  const container = document.getElementById('notes-grid');
  if (!container) return;

  if (notes.length === 0) {
    container.innerHTML = `<div class="col-span-full py-16 text-center text-slate-400 font-medium">No notes found. Click "+ Write New Note" to write one!</div>`;
    return;
  }

  container.innerHTML = notes.map(note => `
    <div class="glass-panel p-5 rounded-2xl relative border-t-4 transition-all hover:shadow-lg hover:-translate-y-1 flex flex-col justify-between" style="border-top-color: ${note.colorTag || '#8b5cf6'}">
      <div>
        <div class="flex items-start justify-between gap-2 mb-2">
          <div class="flex items-center gap-2">
            <span class="text-xs font-semibold px-2.5 py-0.5 rounded-full bg-purple-50 text-purple-700 border border-purple-200">${note.category || 'General'}</span>
            ${note.isPinned ? `<span class="text-xs bg-amber-100 text-amber-700 px-2 py-0.5 rounded-full font-bold">📌 Pinned</span>` : ''}
          </div>
          <div class="flex items-center gap-1">
            <button onclick="togglePinNote(${note.id})" title="${note.isPinned ? 'Unpin' : 'Pin to top'}" class="p-1 hover:text-purple-600 text-slate-400 text-sm">📌</button>
            <button onclick="deleteNote(${note.id})" title="Delete" class="p-1 hover:text-rose-600 text-slate-400 text-sm">&times;</button>
          </div>
        </div>
        <h3 class="font-bold text-slate-800 text-base mb-2 leading-snug">${escapeHtml(note.title)}</h3>
        <p class="text-slate-600 text-sm whitespace-pre-line leading-relaxed mb-4">${escapeHtml(note.content)}</p>
      </div>
      <div class="text-xs text-slate-400 border-t border-slate-100 pt-3 flex items-center justify-between">
        <span>Updated: ${note.updatedAt ? note.updatedAt.substring(0, 10) : 'Recent'}</span>
        <button onclick="editNoteModal(${note.id})" class="text-purple-600 hover:text-purple-800 font-semibold text-xs">Edit Note &rarr;</button>
      </div>
    </div>
  `).join('');
}

function openNoteModal(note = null) {
  document.getElementById('note-modal-id').value = note ? note.id : '';
  document.getElementById('note-modal-title').value = note ? note.title : '';
  document.getElementById('note-modal-content').value = note ? note.content : '';
  document.getElementById('note-modal-category').value = note ? note.category : 'Maintenance';
  document.getElementById('note-modal-color').value = note ? (note.colorTag || '#8b5cf6') : '#8b5cf6';
  document.getElementById('note-modal-pinned').checked = note ? !!note.isPinned : false;
  document.getElementById('note-modal').classList.remove('hidden');
}

async function editNoteModal(id) {
  const notes = await OfficeFlowAPI.getNotes(AppState.currentUser.id);
  const note = notes.find(n => n.id === Number(id));
  if (note) openNoteModal(note);
}

function closeNoteModal() {
  document.getElementById('note-modal').classList.add('hidden');
}

async function handleSaveNote(event) {
  event.preventDefault();
  const id = document.getElementById('note-modal-id').value;
  await OfficeFlowAPI.saveNote({
    id: id ? Number(id) : undefined,
    userId: AppState.currentUser.id,
    title: document.getElementById('note-modal-title').value,
    content: document.getElementById('note-modal-content').value,
    category: document.getElementById('note-modal-category').value,
    colorTag: document.getElementById('note-modal-color').value,
    isPinned: document.getElementById('note-modal-pinned').checked
  });
  closeNoteModal();
  loadNotes();
  showToast('Note Saved', id ? 'Daily note updated' : 'New note added to notepad');
}

async function togglePinNote(id) {
  await OfficeFlowAPI.togglePinNote(id);
  loadNotes();
}

async function deleteNote(id) {
  if (confirm('Delete this note?')) {
    await OfficeFlowAPI.deleteNote(id);
    loadNotes();
    showToast('Note Deleted', '', 'info');
  }
}

// 2. Project Repos
async function loadProjects() {
  const category = document.getElementById('project-filter-category')?.value || '';
  const projects = await OfficeFlowAPI.getProjects(category);
  const container = document.getElementById('projects-grid');
  if (!container) return;

  container.innerHTML = projects.map(proj => `
    <div class="glass-panel p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col justify-between">
      <div>
        <div class="flex items-center justify-between gap-2 mb-3">
          <span class="text-xs font-semibold px-2.5 py-1 rounded-full bg-purple-100 text-purple-800">${proj.category || 'Operations'}</span>
          <span class="text-xs font-bold px-2 py-0.5 rounded-full ${proj.status === 'ACTIVE' ? 'bg-emerald-100 text-emerald-800' : 'bg-amber-100 text-amber-800'}">${proj.status}</span>
        </div>
        <h3 class="font-bold text-lg text-slate-800 mb-1.5">${escapeHtml(proj.projectName)}</h3>
        <p class="text-slate-600 text-sm leading-relaxed mb-4">${escapeHtml(proj.description || '')}</p>
        <div class="mb-4">
          <span class="text-xs font-semibold text-slate-500 uppercase tracking-wider block mb-1">Tech Stack</span>
          <div class="flex flex-wrap gap-1.5">
            ${(proj.techStack || 'Java 17, SSMS').split(',').map(tag => `<span class="text-xs bg-slate-100 text-slate-700 px-2 py-0.5 rounded font-mono">${tag.trim()}</span>`).join('')}
          </div>
        </div>
      </div>
      <div class="border-t border-slate-100 pt-4 flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <a href="${proj.repoUrl}" target="_blank" class="flex-1 text-center py-2 px-3 rounded-xl bg-slate-900 text-white hover:bg-slate-800 text-xs font-semibold">GitHub Repo</a>
          <button onclick="copyToClipboard('${proj.repoUrl}')" class="p-2 border border-slate-200 rounded-xl hover:bg-slate-50 text-xs">📋</button>
        </div>
        ${proj.liveDemoUrl ? `<a href="${proj.liveDemoUrl}" target="_blank" class="text-center py-1 text-purple-700 text-xs font-semibold">Live System Demo &rarr;</a>` : ''}
      </div>
    </div>
  `).join('');
}

function openProjectModal() {
  document.getElementById('project-form').reset();
  document.getElementById('project-modal').classList.remove('hidden');
}

function closeProjectModal() {
  document.getElementById('project-modal').classList.add('hidden');
}

async function handleSaveProject(event) {
  event.preventDefault();
  await OfficeFlowAPI.saveProject({
    userId: AppState.currentUser.id,
    projectName: document.getElementById('proj-name').value,
    description: document.getElementById('proj-desc').value,
    repoUrl: document.getElementById('proj-repo-url').value,
    liveDemoUrl: document.getElementById('proj-demo-url').value,
    documentationUrl: document.getElementById('proj-doc-url').value,
    techStack: document.getElementById('proj-tech').value,
    category: document.getElementById('proj-category').value,
    status: document.getElementById('proj-status').value
  });
  closeProjectModal();
  loadProjects();
  showToast('Project Added', 'Repository card created in SSMS database');
}

// 3. Daily Streaks
async function loadStreaks() {
  const summary = await OfficeFlowAPI.getStreakSummary(AppState.currentUser.id);
  document.getElementById('streak-big-count').innerText = `${summary.currentStreakDays} Days`;
  document.getElementById('streak-badge-title').innerText = summary.currentBadge;
  document.getElementById('streak-days-until').innerText = `${summary.daysUntilNextMilestone} days to next milestone`;
  document.getElementById('streak-total-logged').innerText = summary.totalDaysLogged;

  const logsContainer = document.getElementById('streak-logs-list');
  if (logsContainer && summary.recentLogs) {
    logsContainer.innerHTML = summary.recentLogs.map(log => `
      <div class="flex items-center justify-between p-3.5 rounded-xl bg-slate-50 border border-slate-200">
        <div class="flex items-center gap-3">
          <div class="w-8 h-8 rounded-full bg-emerald-100 text-emerald-700 font-bold flex items-center justify-center text-xs">✓</div>
          <div>
            <h4 class="font-bold text-slate-800 text-sm">${escapeHtml(log.taskSummary)}</h4>
            <p class="text-xs text-slate-500">${log.logDate} &bull; ${log.hoursLogged}h logged ${log.notes ? `&bull; ${escapeHtml(log.notes)}` : ''}</p>
          </div>
        </div>
        <span class="text-xs font-bold text-purple-700 bg-purple-50 px-2.5 py-1 rounded-full border border-purple-200">Day ${log.streakCountSnapshot || 'Active'}</span>
      </div>
    `).join('');
  }
}

async function handleRecordDailyStreak(event) {
  event.preventDefault();
  await OfficeFlowAPI.recordStreak({
    userId: AppState.currentUser.id,
    taskSummary: document.getElementById('streak-input-summary').value,
    hoursLogged: document.getElementById('streak-input-hours').value,
    notes: document.getElementById('streak-input-notes').value,
    logDate: new Date().toISOString().split('T')[0]
  });
  document.getElementById('streak-form').reset();
  loadStreaks();
  showToast('Streak Extended! 🔥', 'Daily maintenance check-in logged to SSMS');
}

// 4. Monthly Reports (Word-Like Document)
let currentEditingReportId = null;

async function loadReports() {
  const reports = await OfficeFlowAPI.getReports();
  const container = document.getElementById('reports-history-list');
  if (!container) return;

  container.innerHTML = reports.map(r => `
    <div class="p-4 rounded-xl border border-slate-200 bg-white hover:border-purple-300 transition flex items-center justify-between">
      <div>
        <div class="flex items-center gap-2 mb-1">
          <span class="text-xs font-bold px-2 py-0.5 rounded-full ${r.status === 'APPROVED' ? 'bg-emerald-100 text-emerald-800' : 'bg-purple-100 text-purple-800'}">${r.status}</span>
          <span class="text-xs text-slate-400">${r.reportMonth}</span>
        </div>
        <h4 class="font-bold text-slate-800 text-sm">${escapeHtml(r.reportTitle)}</h4>
        <p class="text-xs text-slate-500 mt-0.5">${r.totalTasksCompleted || 0} tickets closed &bull; Budget: $${Number(r.maintenanceCost || 0).toLocaleString()}</p>
      </div>
      <button onclick="loadReportIntoEditor(${r.id})" class="px-3 py-1.5 text-xs font-semibold rounded-lg bg-purple-50 text-purple-700 hover:bg-purple-100">Load into Word Editor</button>
    </div>
  `).join('');
}

async function loadReportIntoEditor(reportId) {
  const reports = await OfficeFlowAPI.getReports();
  const report = reports.find(r => r.id === Number(reportId));
  if (!report) return;

  currentEditingReportId = report.id;
  document.getElementById('report-title-input').value = report.reportTitle;
  document.getElementById('report-month-input').value = report.reportMonth;
  document.getElementById('report-dept-input').value = report.department;
  document.getElementById('report-tasks-count').value = report.totalTasksCompleted;
  document.getElementById('report-budget-spent').value = report.maintenanceCost;
  document.getElementById('report-summary-input').value = report.executiveSummary || '';
  document.getElementById('word-editor').innerHTML = report.contentHtml;
  showToast('Report Loaded', `Loaded "${report.reportTitle}"`);
}

function formatDoc(command, value = null) {
  document.execCommand(command, false, value);
  document.getElementById('word-editor').focus();
}

function insertTableIntoDoc() {
  const html = `<table border="1" style="width:100%; border-collapse: collapse; margin: 1rem 0; border: 1px solid #cbd5e1;"><thead><tr style="background:#f1f5f9;"><th style="padding: 8px; border: 1px solid #cbd5e1;">Maintenance Category</th><th style="padding: 8px; border: 1px solid #cbd5e1;">Scheduled Tasks</th><th style="padding: 8px; border: 1px solid #cbd5e1;">Status</th></tr></thead><tbody><tr><td style="padding: 8px; border: 1px solid #cbd5e1;">HVAC & Climate</td><td style="padding: 8px; border: 1px solid #cbd5e1;">Quarterly Filter Exchange</td><td style="padding: 8px; border: 1px solid #cbd5e1; color: green; font-weight: bold;">100% Completed</td></tr></tbody></table>`;
  document.execCommand('insertHTML', false, html);
}

async function handleSaveReportDocument() {
  const title = document.getElementById('report-title-input').value;
  const content = document.getElementById('word-editor').innerHTML;
  if (!title || !content) {
    showToast('Error', 'Title and content required', 'error');
    return;
  }
  await OfficeFlowAPI.saveReport({
    id: currentEditingReportId || undefined,
    userId: AppState.currentUser.id,
    reportTitle: title,
    reportMonth: document.getElementById('report-month-input').value,
    department: document.getElementById('report-dept-input').value,
    totalTasksCompleted: Number(document.getElementById('report-tasks-count').value || 0),
    maintenanceCost: Number(document.getElementById('report-budget-spent').value || 0),
    executiveSummary: document.getElementById('report-summary-input').value,
    contentHtml: content,
    status: AppState.currentUser.role === 'TEAM_LEAD' ? 'APPROVED' : 'SUBMITTED',
    reviewedBy: AppState.currentUser.role === 'TEAM_LEAD' ? AppState.currentUser.name : null
  });
  loadReports();
  showToast('Document Saved', 'Saved report to SSMS database');
}

function handlePrintDocument() {
  window.print();
}

// 5. Old vs New Ideas
async function loadIdeas() {
  const search = document.getElementById('ideas-search-input')?.value || '';
  const ideas = await OfficeFlowAPI.getIdeas(search);
  const container = document.getElementById('ideas-proposals-container');
  if (!container) return;

  const projects = await OfficeFlowAPI.getProjects();
  const dropdown = document.getElementById('idea-project-dropdown');
  if (dropdown) {
    dropdown.innerHTML = `
      <option value="">-- Choose Project for Idea Proposal --</option>
      ${projects.map(p => `<option value="${escapeHtml(p.projectName)}">${escapeHtml(p.projectName)} (${p.category})</option>`).join('')}
      <option value="New Infrastructure Project">+ Propose Brand New Initiative</option>
    `;
  }

  container.innerHTML = ideas.map(idea => `
    <div class="glass-panel p-6 rounded-2xl border border-purple-100 shadow-sm flex flex-col gap-4">
      <div class="flex items-center justify-between flex-wrap gap-2 pb-3 border-b border-slate-100">
        <div>
          <span class="text-xs font-bold text-purple-700 bg-purple-100 px-2.5 py-0.5 rounded-full mr-2">${escapeHtml(idea.projectName)}</span>
          <span class="text-xs text-slate-400">Proposed by ${idea.user ? idea.user.name : 'Team Member'} &bull; Impact: <b>${idea.estimatedImpact}</b></span>
        </div>
        <div class="flex items-center gap-2">
          <span class="text-xs font-bold px-3 py-1 rounded-full ${idea.status === 'ACCEPTED' ? 'bg-emerald-100 text-emerald-800' : 'bg-purple-100 text-purple-800'}">${idea.status}</span>
          ${AppState.currentUser.role === 'TEAM_LEAD' && idea.status !== 'ACCEPTED' ? `<button onclick="acceptIdeaProposal(${idea.id})" class="px-2.5 py-1 text-xs font-bold rounded-lg bg-emerald-600 text-white">Approve Idea</button>` : ''}
        </div>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="p-4 rounded-xl bg-rose-50/70 border border-rose-200">
          <div class="text-rose-800 font-bold text-xs uppercase tracking-wider mb-2">⚠️ Old Project Idea / Bottleneck</div>
          <p class="text-slate-700 text-sm leading-relaxed">${escapeHtml(idea.oldIdeaDescription)}</p>
        </div>
        <div class="p-4 rounded-xl bg-purple-50/80 border border-purple-200">
          <div class="text-purple-900 font-bold text-xs uppercase tracking-wider mb-2">💡 New Suggestion Idea & Innovation</div>
          <p class="text-slate-800 text-sm font-medium leading-relaxed">${escapeHtml(idea.newSuggestionIdea)}</p>
          ${idea.benefits ? `<p class="mt-2 text-xs text-purple-700"><b>Benefits:</b> ${escapeHtml(idea.benefits)}</p>` : ''}
        </div>
      </div>
      ${idea.leadFeedback ? `<div class="p-3 rounded-lg bg-emerald-50 text-emerald-800 text-xs border border-emerald-200"><b>Team Lead Feedback:</b> ${escapeHtml(idea.leadFeedback)}</div>` : ''}
    </div>
  `).join('');
}

async function handleSaveIdeaProposal(event) {
  event.preventDefault();
  const dropdown = document.getElementById('idea-project-dropdown');
  const projectName = dropdown.value;
  if (!projectName) {
    showToast('Select Project', 'Please choose a project from the dropdown', 'error');
    return;
  }
  await OfficeFlowAPI.saveIdea({
    userId: AppState.currentUser.id,
    projectName: projectName,
    oldIdeaDescription: document.getElementById('idea-old-writing-field').value,
    newSuggestionIdea: document.getElementById('idea-new-writing-field').value,
    benefits: document.getElementById('idea-benefits-field').value,
    estimatedImpact: document.getElementById('idea-impact-select').value
  });
  document.getElementById('idea-proposal-form').reset();
  loadIdeas();
  showToast('Proposal Submitted!', 'Old vs new idea comparison saved to SSMS');
}

async function acceptIdeaProposal(id) {
  const feedback = prompt('Enter approval notes or budget allocation:');
  if (feedback !== null) {
    await OfficeFlowAPI.reviewIdea(id, 'ACCEPTED', feedback || 'Approved by Team Lead');
    loadIdeas();
    showToast('Proposal Approved', 'Idea marked Accepted in SSMS');
  }
}

// 6. Performance Evaluation
async function loadPerformance() {
  const evals = await OfficeFlowAPI.getEvaluations();
  const container = document.getElementById('performance-eval-list');
  if (!container) return;

  container.innerHTML = evals.map(ev => `
    <div class="glass-panel p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col gap-4">
      <div class="flex items-center justify-between flex-wrap gap-2 pb-3 border-b border-slate-100">
        <div class="flex items-center gap-3">
          <div class="w-12 h-12 rounded-2xl bg-purple-600 text-white font-extrabold text-xl flex items-center justify-center">${ev.grade || 'A'}</div>
          <div>
            <h3 class="font-bold text-slate-800 text-base">${ev.user ? ev.user.name : 'Team Member'}</h3>
            <p class="text-xs text-slate-500">${ev.user ? (ev.user.designation || 'Specialist') : ''} &bull; ${ev.evaluationMonth}</p>
          </div>
        </div>
        <div class="text-right">
          <span class="text-2xl font-black text-purple-700">${ev.overallScore}%</span>
          <span class="block text-xs text-slate-400">Weighted Performance</span>
        </div>
      </div>
      <div class="grid grid-cols-2 md:grid-cols-4 gap-3">
        <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 text-center"><span class="text-xs text-slate-500 block">SLA (35%)</span><span class="font-extrabold text-slate-800 text-lg">${ev.maintenanceSlaScore}%</span></div>
        <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 text-center"><span class="text-xs text-slate-500 block">Uptime (25%)</span><span class="font-extrabold text-slate-800 text-lg">${ev.uptimeReliabilityScore}%</span></div>
        <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 text-center"><span class="text-xs text-slate-500 block">Speed (25%)</span><span class="font-extrabold text-slate-800 text-lg">${ev.taskCompletionScore}%</span></div>
        <div class="p-3 rounded-xl bg-slate-50 border border-slate-200 text-center"><span class="text-xs text-slate-500 block">Team (15%)</span><span class="font-extrabold text-slate-800 text-lg">${ev.teamworkInitiativeScore}%</span></div>
      </div>
      <div class="text-xs text-slate-600 bg-purple-50/60 p-3.5 rounded-xl border border-purple-100 space-y-1">
        <p><b>Strengths:</b> ${escapeHtml(ev.strengths || 'Consistent operations')}</p>
        <p><b>Improvement:</b> ${escapeHtml(ev.areasOfImprovement || 'Documentation')}</p>
        ${ev.leadComments ? `<p class="text-purple-800"><b>Lead Comments:</b> "${escapeHtml(ev.leadComments)}"</p>` : ''}
      </div>
    </div>
  `).join('');
}

function openEvaluationModal() {
  document.getElementById('eval-form').reset();
  document.getElementById('eval-modal').classList.remove('hidden');
}

function closeEvaluationModal() {
  document.getElementById('eval-modal').classList.add('hidden');
}

async function handleSaveEvaluation(event) {
  event.preventDefault();
  await OfficeFlowAPI.saveEvaluation({
    userId: Number(document.getElementById('eval-user-select').value),
    evaluatorId: AppState.currentUser.id,
    evaluationMonth: document.getElementById('eval-month-select').value,
    maintenanceSlaScore: Number(document.getElementById('eval-sla').value),
    uptimeReliabilityScore: Number(document.getElementById('eval-uptime').value),
    taskCompletionScore: Number(document.getElementById('eval-completion').value),
    teamworkInitiativeScore: Number(document.getElementById('eval-teamwork').value),
    strengths: document.getElementById('eval-strengths').value,
    areasOfImprovement: document.getElementById('eval-improvement').value,
    leadComments: document.getElementById('eval-comments').value
  });
  closeEvaluationModal();
  loadPerformance();
  showToast('Evaluation Published', 'Saved evaluation scorecard to SSMS');
}

// 7. Team Excel Sheet
async function loadTaskSheets() {
  const sheets = await OfficeFlowAPI.getTaskSheets();
  const container = document.getElementById('tasksheets-container');
  if (!container) return;

  container.innerHTML = sheets.map(sheet => {
    const totalRows = (sheet.rows || []).length;
    const completedRows = (sheet.rows || []).filter(r => r.status === 'COMPLETED').length;
    const pct = totalRows > 0 ? Math.round((completedRows / totalRows) * 100) : 0;

    return `
      <div class="glass-panel p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col gap-4">
        <div class="flex items-center justify-between flex-wrap gap-3 pb-3 border-b border-slate-100">
          <div>
            <span class="text-xs font-bold px-2 py-0.5 rounded-full bg-emerald-100 text-emerald-800">Active Spreadsheet</span>
            <h3 class="font-bold text-slate-800 text-lg mt-1">${escapeHtml(sheet.sheetTitle)}</h3>
            <p class="text-xs text-slate-500">${escapeHtml(sheet.sheetDescription || '')}</p>
          </div>
          <div class="text-right">
            <span class="text-sm font-bold text-slate-700">${completedRows}/${totalRows} Done (${pct}%)</span>
            <div class="w-32 h-2 rounded-full bg-slate-100 overflow-hidden mt-1"><div class="h-full bg-purple-600 rounded-full" style="width: ${pct}%"></div></div>
          </div>
        </div>
        <div class="overflow-x-auto rounded-xl border border-slate-200">
          <table class="w-full text-left excel-table border-collapse">
            <thead>
              <tr>
                <th class="p-3 w-12 text-center">#</th>
                <th class="p-3 min-w-[200px]">Maintenance Task</th>
                <th class="p-3 min-w-[120px]">Category</th>
                <th class="p-3 min-w-[100px]">Priority</th>
                <th class="p-3 min-w-[140px]">Assigned Member</th>
                <th class="p-3 min-w-[280px]">Member Work Update (Editable)</th>
                <th class="p-3 w-24 text-center">Hours</th>
                <th class="p-3 min-w-[130px]">Status</th>
                <th class="p-3 w-24 text-center">Action</th>
              </tr>
            </thead>
            <tbody>
              ${(sheet.rows || []).map((row, idx) => `
                <tr>
                  <td class="p-3 text-center text-xs font-mono text-slate-400">${idx + 1}</td>
                  <td class="p-3 font-semibold text-slate-800">${escapeHtml(row.taskName)}</td>
                  <td class="p-3 text-xs text-slate-600">${escapeHtml(row.category || 'Maintenance')}</td>
                  <td class="p-3"><span class="text-xs font-bold px-2 py-0.5 rounded ${row.priority === 'URGENT' ? 'bg-rose-100 text-rose-700' : 'bg-slate-100 text-slate-700'}">${row.priority}</span></td>
                  <td class="p-3 text-xs font-medium text-slate-700">${escapeHtml(row.assignedMemberName || 'Unassigned')}</td>
                  <td class="p-3"><input type="text" id="row-update-input-${row.id}" value="${escapeHtml(row.memberWorkUpdate || '')}" class="w-full px-2.5 py-1.5 text-xs rounded-lg border border-slate-200 focus:border-purple-500" /></td>
                  <td class="p-3 text-center"><input type="number" id="row-hours-input-${row.id}" step="0.5" value="${row.timeSpentHours || 0}" class="w-16 px-1.5 py-1 text-xs text-center rounded-lg border border-slate-200" /></td>
                  <td class="p-3">
                    <select id="row-status-select-${row.id}" class="px-2 py-1 text-xs rounded-lg border border-slate-200 font-semibold">
                      <option value="PENDING" ${row.status === 'PENDING' ? 'selected' : ''}>Pending</option>
                      <option value="IN_PROGRESS" ${row.status === 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                      <option value="COMPLETED" ${row.status === 'COMPLETED' ? 'selected' : ''}>Completed</option>
                      <option value="BLOCKED" ${row.status === 'BLOCKED' ? 'selected' : ''}>Blocked</option>
                    </select>
                  </td>
                  <td class="p-3 text-center"><button onclick="saveExcelRowUpdate(${row.id})" class="px-2.5 py-1 text-xs font-bold rounded-lg bg-purple-600 text-white hover:bg-purple-700">Save</button></td>
                </tr>
              `).join('')}
            </tbody>
          </table>
        </div>
      </div>
    `;
  }).join('');
}

async function saveExcelRowUpdate(rowId) {
  await OfficeFlowAPI.updateTaskRow(rowId, {
    memberWorkUpdate: document.getElementById(`row-update-input-${rowId}`).value,
    timeSpentHours: Number(document.getElementById(`row-hours-input-${rowId}`).value),
    status: document.getElementById(`row-status-select-${rowId}`).value,
    lastUpdatedBy: AppState.currentUser.name
  });
  showToast('Row Updated', `Updated by ${AppState.currentUser.name}`);
  loadTaskSheets();
}

function openCreateSheetModal() {
  document.getElementById('tasksheet-form').reset();
  document.getElementById('tasksheet-modal').classList.remove('hidden');
}

function closeCreateSheetModal() {
  document.getElementById('tasksheet-modal').classList.add('hidden');
}

async function handleCreateTaskSheet(event) {
  event.preventDefault();
  const rows = [];
  const r1 = document.getElementById('sheet-row1-name').value;
  const m1 = document.getElementById('sheet-row1-member').value;
  if (r1) rows.push({ taskName: r1, assignedMemberName: m1, priority: 'HIGH', category: 'Maintenance' });
  const r2 = document.getElementById('sheet-row2-name').value;
  const m2 = document.getElementById('sheet-row2-member').value;
  if (r2) rows.push({ taskName: r2, assignedMemberName: m2, priority: 'MEDIUM', category: 'Inspection' });

  await OfficeFlowAPI.saveTaskSheet({
    leadId: AppState.currentUser.id,
    sheetTitle: document.getElementById('sheet-title').value,
    sheetDescription: document.getElementById('sheet-desc').value,
    targetDate: document.getElementById('sheet-target-date').value,
    rows: rows
  });
  closeCreateSheetModal();
  loadTaskSheets();
  showToast('Sheet Distributed!', 'Task spreadsheet sent to team via SSMS');
}

// 8. Organization Email Dispatch
async function loadEmails() {
  const grants = await OfficeFlowAPI.getEmailGrants();
  const logs = await OfficeFlowAPI.getEmailLogs();

  const grantsContainer = document.getElementById('email-grants-table-body');
  if (grantsContainer) {
    grantsContainer.innerHTML = grants.map(g => `
      <tr class="border-b border-slate-100">
        <td class="p-3 text-xs font-bold text-slate-800">${g.user ? g.user.name : 'Team Member'}</td>
        <td class="p-3 text-xs font-mono text-purple-700 font-semibold">${g.grantedOrgEmail}</td>
        <td class="p-3 text-xs text-slate-600">${g.permissions || 'All'}</td>
        <td class="p-3"><span class="text-xs font-bold px-2 py-0.5 rounded-full ${g.status === 'ACTIVE' ? 'bg-emerald-100 text-emerald-800' : 'bg-rose-100 text-rose-800'}">${g.status}</span></td>
        <td class="p-3 text-center">
          ${AppState.currentUser.role === 'TEAM_LEAD' ? `<button onclick="${g.status === 'ACTIVE' ? `revokeEmailAccess(${g.id})` : `alert('Access inactive')`}" class="text-xs font-semibold ${g.status === 'ACTIVE' ? 'text-rose-600' : 'text-slate-400'}">${g.status === 'ACTIVE' ? 'Revoke Access' : 'Inactive'}</button>` : `<span class="text-xs text-slate-400">Lead Only</span>`}
        </td>
      </tr>
    `).join('');
  }

  const logsContainer = document.getElementById('email-logs-list');
  if (logsContainer) {
    logsContainer.innerHTML = logs.map(l => `
      <div class="p-4 rounded-xl border border-slate-200 bg-white hover:border-purple-300 transition flex flex-col gap-2">
        <div class="flex items-center justify-between flex-wrap gap-2 text-xs">
          <span class="font-bold px-2 py-0.5 rounded-full bg-purple-100 text-purple-800">${l.emailType}</span>
          <span class="text-slate-400">${l.sentAt}</span>
        </div>
        <h4 class="font-bold text-slate-800 text-sm">${escapeHtml(l.subject)}</h4>
        <p class="text-xs text-slate-600 whitespace-pre-line bg-slate-50 p-2.5 rounded-lg border border-slate-100">${escapeHtml(l.emailBody)}</p>
      </div>
    `).join('');
  }

  const senderDisplay = document.getElementById('email-composer-from');
  if (senderDisplay) {
    senderDisplay.value = AppState.currentUser.organizationEmail || 'ACCESS REQUIRED - Ask Team Lead';
  }
}

async function handleGrantEmailModal(event) {
  event.preventDefault();
  await OfficeFlowAPI.grantEmailAccess({
    leadId: AppState.currentUser.id,
    userId: Number(document.getElementById('grant-user-select').value),
    grantedOrgEmail: document.getElementById('grant-org-email').value,
    grantNotes: document.getElementById('grant-notes').value
  });
  document.getElementById('grant-email-modal').classList.add('hidden');
  loadEmails();
  showToast('Email Access Granted!', 'Corporate email recorded in SSMS');
}

async function revokeEmailAccess(id) {
  if (confirm('Revoke corporate email access?')) {
    await OfficeFlowAPI.revokeEmailAccess(id);
    loadEmails();
    showToast('Access Revoked', '', 'info');
  }
}

async function handleSendOrganizationEmail(event) {
  event.preventDefault();
  if (!AppState.currentUser.hasOrgEmailAccess || !AppState.currentUser.organizationEmail) {
    showToast('Permission Denied', 'Request email access from Team Lead', 'error');
    return;
  }
  await OfficeFlowAPI.sendOrgEmail({
    senderUserId: AppState.currentUser.id,
    recipientType: document.getElementById('email-target-select').value,
    recipientEmails: document.getElementById('email-recipients-input').value,
    emailType: document.getElementById('email-type-select').value,
    subject: document.getElementById('email-subject-input').value,
    emailBody: document.getElementById('email-body-input').value
  });
  document.getElementById('email-composer-form').reset();
  loadEmails();
  showToast('Email Dispatched! ✉️', 'Sent and logged to SSMS');
}

// Utilities
function escapeHtml(text) {
  if (!text) return '';
  return String(text).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#039;');
}

function copyToClipboard(text) {
  navigator.clipboard.writeText(text).then(() => showToast('Copied to Clipboard!', text));
}

window.addEventListener('DOMContentLoaded', () => {
  updateUserHeaderDisplay();
  updateBackendStatus();
  setInterval(updateBackendStatus, 5000); // Check every 5 seconds
  switchTab('dashboard');
});
