package com.officeflow.repository;

import com.officeflow.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserIdOrderByIsPinnedDescUpdatedAtDesc(Long userId);
    List<Note> findByUserIdAndCategory(Long userId, String category);

    @Query("SELECT n FROM Note n WHERE n.user.id = :userId AND " +
           "(LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Note> searchUserNotes(Long userId, String keyword);
}
