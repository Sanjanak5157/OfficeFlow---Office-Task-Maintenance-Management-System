package com.officeflow.service;

import com.officeflow.entity.StreakLog;
import java.util.Map;

public interface StreakService {
    Map<String, Object> getStreakSummary(Long userId);
    StreakLog saveStreak(StreakLog streakLog);
}
