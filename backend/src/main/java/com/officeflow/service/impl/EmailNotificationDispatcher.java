package com.officeflow.service.impl;

import com.officeflow.entity.OrgEmailLog;
import com.officeflow.repository.OrgEmailLogRepository;
import com.officeflow.service.NotificationDispatcher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailNotificationDispatcher implements NotificationDispatcher {

    private final OrgEmailLogRepository logRepository;

    public EmailNotificationDispatcher(OrgEmailLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public OrgEmailLog dispatch(OrgEmailLog emailLog) {
        emailLog.setSentAt(LocalDateTime.now());
        emailLog.setSentStatus("SENT");
        return logRepository.save(emailLog);
    }

    @Override
    public String getDispatcherChannel() {
        return "SSMS_EMAIL_GATEWAY";
    }
}
