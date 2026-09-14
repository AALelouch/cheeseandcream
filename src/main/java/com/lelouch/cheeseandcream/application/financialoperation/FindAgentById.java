package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.domain.Agent;
import java.util.Optional;

public interface FindAgentById {

    Optional<Agent> findById(Long agentId);

}
