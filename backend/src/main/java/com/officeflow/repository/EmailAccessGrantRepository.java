package com.officeflow.repository;

import com.officeflow.entity.EmailAccessGrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailAccessGrantRepository extends JpaRepository<EmailAccessGrant, Long> {
    List<EmailAccessGrant> findAllByOrderByCreatedAtDesc();
    Optional<EmailAccessGrant> findByUserIdAndStatus(Long userId, String status);
    List<EmailAccessGrant> findByLeadId(Long leadId);
}
