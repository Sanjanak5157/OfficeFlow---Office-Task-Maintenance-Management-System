package com.officeflow.repository;

import com.officeflow.entity.OrgEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgEmailLogRepository extends JpaRepository<OrgEmailLog, Long> {
    List<OrgEmailLog> findAllByOrderBySentAtDesc();
    List<OrgEmailLog> findBySenderUserIdOrderBySentAtDesc(Long senderUserId);
    List<OrgEmailLog> findByEmailType(String emailType);
}
