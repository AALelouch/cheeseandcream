package com.lelouch.cheeseandcream.application.service;

import com.lelouch.cheeseandcream.application.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.application.model.agent.AgentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentCrudService {

    void createAgent(AgentRequest agentData);
    void updateAgent(Long agentId, AgentRequest agentData);
    void deleteAgent(Long agentId);
    AgentResponse getAgent(Long agentId);
    Page<AgentResponse> getAllAgents(Pageable pageable);

}
