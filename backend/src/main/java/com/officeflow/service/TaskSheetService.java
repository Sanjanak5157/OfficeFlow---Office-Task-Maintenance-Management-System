package com.officeflow.service;

import com.officeflow.entity.TaskRow;
import com.officeflow.entity.TaskSheet;
import java.util.List;

public interface TaskSheetService {
    List<TaskSheet> getAllSheets();
    TaskSheet saveSheet(TaskSheet sheet);
    TaskRow updateTaskRow(Long rowId, TaskRow rowData);
}
