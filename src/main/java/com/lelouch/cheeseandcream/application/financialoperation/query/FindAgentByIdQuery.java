package com.lelouch.cheeseandcream.application.financialoperation.query;

import com.lelouch.cheeseandcream.domain.Agent;
import java.util.Optional;

public interface FindAgentByIdQuery {

    Optional<Agent> findById(Long agentId);

}
