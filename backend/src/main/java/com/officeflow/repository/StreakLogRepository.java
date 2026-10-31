package com.officeflow.repository;

import com.officeflow.entity.StreakLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StreakLogRepository extends JpaRepository<StreakLog, Long> {
    List<StreakLog> findByUserIdOrderByLogDateDesc(Long userId);
    Optional<StreakLog> findByUserIdAndLogDate(Long userId, LocalDate logDate);

    @Query("SELECT COUNT(s) FROM StreakLog s WHERE s.user.id = :userId AND s.status = 'COMPLETED'")
    long countCompletedDays(Long userId);
}
