package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.domain.Agent;
import org.springframework.data.domain.Page;

public interface AgentOutputPort {

    AgentResponse mapToResponse(Agent agent);
    Page<AgentResponse> mapToResponse(Page<Agent> agents);
}
