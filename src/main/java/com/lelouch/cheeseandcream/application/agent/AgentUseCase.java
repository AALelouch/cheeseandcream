package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentTermRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentUseCase {

    void createAgent(AgentRequest agentRequest);
    void updateAgent(Long agentId, AgentRequest agentRequest);
    void deleteAgent(Long agentId);
    AgentResponse getAgent(Long agentId);
    Page<AgentResponse> getAllAgents(Pageable pageable);
    Page<AgentResponse> getAgentsWithProducts(Pageable pageable);
    Page<AgentResponse> searchAgents(AgentTermRequest term, Pageable pageable);
    Page<AgentResponse> getAgentsWithProductsByTerm(AgentTermRequest term, Pageable pageable);
}
