package com.officeflow.repository;

import com.officeflow.entity.TaskSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskSheetRepository extends JpaRepository<TaskSheet, Long> {
    List<TaskSheet> findAllByOrderByCreatedAtDesc();
    List<TaskSheet> findByIsActiveTrue();
    List<TaskSheet> findByLeadId(Long leadId);
}
