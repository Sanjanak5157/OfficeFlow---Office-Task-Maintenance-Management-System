package com.officeflow.service;

import com.officeflow.entity.IdeaProposal;
import java.util.List;

public interface IdeaProposalService {
    List<IdeaProposal> getAllIdeas(String search);
    IdeaProposal saveIdea(IdeaProposal idea);
    IdeaProposal reviewIdea(Long id, String status, String leadFeedback);
}
