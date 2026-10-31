package com.officeflow.service;

import com.officeflow.entity.OrgEmailLog;

/**
 * Interface demonstrating OOP Polymorphism for message dispatching.
 */
public interface NotificationDispatcher {
    OrgEmailLog dispatch(OrgEmailLog emailLog);
    String getDispatcherChannel();
}
