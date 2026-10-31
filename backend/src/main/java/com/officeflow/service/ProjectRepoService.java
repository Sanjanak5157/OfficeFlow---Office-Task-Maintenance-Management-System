package com.officeflow.service;

import com.officeflow.entity.ProjectRepo;
import java.util.List;

public interface ProjectRepoService {
    List<ProjectRepo> getAllProjects(String category);
    ProjectRepo saveProject(ProjectRepo project);
    void deleteProject(Long id);
}
