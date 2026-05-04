package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.model.agent.AgentResponse;
import java.util.List;

public interface AgentCrudService {

    void createAgent(AgentRequest agentData);
    void updateAgent(Long agentId, AgentRequest agentData);
    void deleteAgent(Long agentId);
    AgentResponse getAgent(Long agentId);
    List<AgentResponse> getAllAgents();

}
