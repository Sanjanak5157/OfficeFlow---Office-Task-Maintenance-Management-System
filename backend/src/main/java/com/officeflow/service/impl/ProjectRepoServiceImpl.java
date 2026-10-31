package com.officeflow.service.impl;

import com.officeflow.entity.ProjectRepo;
import com.officeflow.entity.User;
import com.officeflow.repository.ProjectRepoRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.ProjectRepoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRepoServiceImpl implements ProjectRepoService {

    private final ProjectRepoRepository projectRepoRepository;
    private final UserRepository userRepository;

    public ProjectRepoServiceImpl(ProjectRepoRepository projectRepoRepository, UserRepository userRepository) {
        this.projectRepoRepository = projectRepoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectRepo> getAllProjects(String category) {
        if (category != null && !category.trim().isEmpty()) {
            return projectRepoRepository.findByCategory(category.trim());
        }
        return projectRepoRepository.findAllByOrderByUpdatedAtDesc();
    }

    @Override
    public ProjectRepo saveProject(ProjectRepo project) {
        if (project.getUser() == null && project.getUserId() != null) {
            User creator = userRepository.findById(project.getUserId()).orElse(null);
            project.setUser(creator);
        } else if (project.getUser() == null) {
            project.setUser(userRepository.findById(1L).orElse(null));
        }
        return projectRepoRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepoRepository.deleteById(id);
    }
}
