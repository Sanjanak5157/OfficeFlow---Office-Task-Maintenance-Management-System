package com.officeflow.service.impl;

import com.officeflow.entity.TaskRow;
import com.officeflow.entity.TaskSheet;
import com.officeflow.repository.TaskRowRepository;
import com.officeflow.repository.TaskSheetRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.TaskSheetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskSheetServiceImpl implements TaskSheetService {

    private final TaskSheetRepository taskSheetRepository;
    private final TaskRowRepository taskRowRepository;
    private final UserRepository userRepository;

    public TaskSheetServiceImpl(TaskSheetRepository taskSheetRepository, TaskRowRepository taskRowRepository, UserRepository userRepository) {
        this.taskSheetRepository = taskSheetRepository;
        this.taskRowRepository = taskRowRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskSheet> getAllSheets() {
        return taskSheetRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public TaskSheet saveSheet(TaskSheet sheet) {
        if (sheet.getLead() == null) {
            sheet.setLead(userRepository.findById(1L).orElse(null));
        }

        // Connect back-reference on rows for JPA
        if (sheet.getRows() != null) {
            for (TaskRow row : sheet.getRows()) {
                row.setTaskSheet(sheet);
            }
        }

        return taskSheetRepository.save(sheet);
    }

    @Override
    public TaskRow updateTaskRow(Long rowId, TaskRow rowData) {
        TaskRow existing = taskRowRepository.findById(rowId).orElse(null);
        if (existing != null) {
            if (rowData.getMemberWorkUpdate() != null) existing.setMemberWorkUpdate(rowData.getMemberWorkUpdate());
            if (rowData.getTimeSpentHours() != null) existing.setTimeSpentHours(rowData.getTimeSpentHours());
            if (rowData.getStatus() != null) existing.setStatus(rowData.getStatus());
            if (rowData.getLastUpdatedBy() != null) existing.setLastUpdatedBy(rowData.getLastUpdatedBy());
            return taskRowRepository.save(existing);
        }
        return null;
    }
}
