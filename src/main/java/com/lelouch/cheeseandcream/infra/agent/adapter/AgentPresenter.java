package com.lelouch.cheeseandcream.infra.agent.adapter;

import com.lelouch.cheeseandcream.application.agent.AgentOutputPort;
import com.lelouch.cheeseandcream.application.agent.AgentResponse;
import com.lelouch.cheeseandcream.domain.Agent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AgentPresenter implements AgentOutputPort {

    @Override
    public AgentResponse mapToResponse(Agent agent) {
        return new AgentResponse(agent.getId(), agent.getName(), agent.getEmail(), agent.getPhoneNumber(),
                agent.getAddress(), agent.getBalance(), String.valueOf(agent.getIdentificationTypeId()),
                agent.getIdentificationNumber());
    }

    @Override
    public Page<AgentResponse> mapToResponse(Page<Agent> agents) {
        return agents.map(this::mapToResponse);
    }
}
