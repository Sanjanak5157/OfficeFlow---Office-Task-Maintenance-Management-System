package com.officeflow.service.impl;

import com.officeflow.entity.EmailAccessGrant;
import com.officeflow.entity.OrgEmailLog;
import com.officeflow.entity.User;
import com.officeflow.repository.EmailAccessGrantRepository;
import com.officeflow.repository.OrgEmailLogRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.EmailAccessService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailAccessServiceImpl implements EmailAccessService {

    private final EmailAccessGrantRepository grantRepository;
    private final OrgEmailLogRepository logRepository;
    private final UserRepository userRepository;

    public EmailAccessServiceImpl(EmailAccessGrantRepository grantRepository,
                                  OrgEmailLogRepository logRepository,
                                  UserRepository userRepository) {
        this.grantRepository = grantRepository;
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<EmailAccessGrant> getEmailGrants() {
        return grantRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public EmailAccessGrant grantEmailAccess(EmailAccessGrant grant) {
        if (grant.getUser() == null && grant.getUserId() != null) {
            User target = userRepository.findById(grant.getUserId()).orElse(null);
            grant.setUser(target);
            if (target != null) {
                target.setHasOrgEmailAccess(true);
                target.setOrganizationEmail(grant.getGrantedOrgEmail());
                userRepository.save(target);
            }
        }
        if (grant.getLead() == null) {
            grant.setLead(userRepository.findById(1L).orElse(null));
        }
        grant.setStatus("ACTIVE");
        return grantRepository.save(grant);
    }

    @Override
    public boolean revokeEmailAccess(Long grantId) {
        EmailAccessGrant grant = grantRepository.findById(grantId).orElse(null);
        if (grant != null) {
            grant.setStatus("REVOKED");
            if (grant.getUser() != null) {
                User user = grant.getUser();
                user.setHasOrgEmailAccess(false);
                userRepository.save(user);
            }
            grantRepository.save(grant);
            return true;
        }
        return false;
    }

    @Override
    public OrgEmailLog sendEmail(OrgEmailLog emailLog) {
        if (emailLog.getSenderUser() == null && emailLog.getSenderUserId() != null) {
            User sender = userRepository.findById(emailLog.getSenderUserId()).orElse(null);
            emailLog.setSenderUser(sender);
        } else if (emailLog.getSenderUser() == null) {
            emailLog.setSenderUser(userRepository.findById(1L).orElse(null));
        }

        emailLog.setSentAt(LocalDateTime.now());
        emailLog.setSentStatus("SENT");
        return logRepository.save(emailLog);
    }

    @Override
    public List<OrgEmailLog> getEmailLogs() {
        return logRepository.findAllByOrderBySentAtDesc();
    }
}
