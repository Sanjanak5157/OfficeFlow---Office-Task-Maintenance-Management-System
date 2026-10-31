package com.officeflow.service;

import com.officeflow.entity.EmailAccessGrant;
import com.officeflow.entity.OrgEmailLog;
import java.util.List;

public interface EmailAccessService {
    List<EmailAccessGrant> getEmailGrants();
    EmailAccessGrant grantEmailAccess(EmailAccessGrant grant);
    boolean revokeEmailAccess(Long grantId);
    OrgEmailLog sendEmail(OrgEmailLog emailLog);
    List<OrgEmailLog> getEmailLogs();
}
