package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.service.model.AgentRequest;
import com.lelouch.cheeseandcream.service.model.AgentResponse;
import java.util.List;

public interface AgentCrudService {

    void createAgent(AgentRequest agentData);
    void updateAgent(Long agentId, AgentRequest agentData);
    void deleteAgent(Long agentId);
    AgentResponse getAgent(Long agentId);
    List<AgentResponse> getAllAgents();

}
