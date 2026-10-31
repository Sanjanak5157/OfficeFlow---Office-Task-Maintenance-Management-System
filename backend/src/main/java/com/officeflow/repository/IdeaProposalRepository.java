package com.officeflow.repository;

import com.officeflow.entity.IdeaProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaProposalRepository extends JpaRepository<IdeaProposal, Long> {
    List<IdeaProposal> findAllByOrderByCreatedAtDesc();
    List<IdeaProposal> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<IdeaProposal> findByProjectName(String projectName);

    @Query("SELECT i FROM IdeaProposal i WHERE " +
           "LOWER(i.projectName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.oldIdeaDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.newSuggestionIdea) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<IdeaProposal> searchProposals(String keyword);
}
