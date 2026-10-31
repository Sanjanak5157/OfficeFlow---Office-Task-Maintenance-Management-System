package com.officeflow.repository;

import com.officeflow.entity.TaskRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRowRepository extends JpaRepository<TaskRow, Long> {
    List<TaskRow> findByTaskSheetId(Long taskSheetId);
    List<TaskRow> findByAssignedUserId(Long assignedUserId);
    List<TaskRow> findByStatus(String status);
}
