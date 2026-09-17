package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.domain.Agent;
import java.util.Optional;

public interface FindAgentByIdQuery {

    Optional<Agent> findById(Long agentId);
}
