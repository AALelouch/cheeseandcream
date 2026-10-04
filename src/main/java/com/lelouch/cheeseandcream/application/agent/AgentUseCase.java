package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.application.agent.dto.AgentIdNameResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentTermRequest;
import com.lelouch.cheeseandcream.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentUseCase {

    void createAgent(AgentRequest agentRequest);
    void updateAgent(Long agentId, AgentRequest agentRequest);
    void deleteAgent(Long agentId);
    AgentResponse getAgent(Long agentId);
    Page<AgentResponse> getAllAgents(Pageable pageable, Role role);
    Page<AgentResponse> searchAgents(AgentTermRequest term, Role role, Pageable pageable);
    Page<AgentIdNameResponse> searchAgentIdNameResponse(AgentTermRequest term, Role role, Pageable pageable);

}
