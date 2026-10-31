package com.officeflow.service.impl;

import com.officeflow.entity.IdeaProposal;
import com.officeflow.entity.User;
import com.officeflow.repository.IdeaProposalRepository;
import com.officeflow.repository.UserRepository;
import com.officeflow.service.IdeaProposalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeaProposalServiceImpl implements IdeaProposalService {

    private final IdeaProposalRepository ideaProposalRepository;
    private final UserRepository userRepository;

    public IdeaProposalServiceImpl(IdeaProposalRepository ideaProposalRepository, UserRepository userRepository) {
        this.ideaProposalRepository = ideaProposalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<IdeaProposal> getAllIdeas(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return ideaProposalRepository.searchProposals(search.trim());
        }
        return ideaProposalRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public IdeaProposal saveIdea(IdeaProposal idea) {
        if (idea.getUser() == null && idea.getUserId() != null) {
            User user = userRepository.findById(idea.getUserId()).orElse(null);
            idea.setUser(user);
        } else if (idea.getUser() == null) {
            idea.setUser(userRepository.findById(1L).orElse(null));
        }
        if (idea.getStatus() == null) {
            idea.setStatus("PROPOSED");
        }
        return ideaProposalRepository.save(idea);
    }

    @Override
    public IdeaProposal reviewIdea(Long id, String status, String leadFeedback) {
        IdeaProposal idea = ideaProposalRepository.findById(id).orElse(null);
        if (idea != null) {
            if (status != null) idea.setStatus(status);
            if (leadFeedback != null) idea.setLeadFeedback(leadFeedback);
            return ideaProposalRepository.save(idea);
        }
        return null;
    }
}
