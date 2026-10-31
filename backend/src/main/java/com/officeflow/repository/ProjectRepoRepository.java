package com.officeflow.repository;

import com.officeflow.entity.ProjectRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepoRepository extends JpaRepository<ProjectRepo, Long> {
    List<ProjectRepo> findAllByOrderByUpdatedAtDesc();
    List<ProjectRepo> findByCategory(String category);
    List<ProjectRepo> findByStatus(String status);
}
