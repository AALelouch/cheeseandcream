package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.query.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JpaFindAgentByIdQueryAdapter implements FindAgentByIdQuery {

    private final com.lelouch.cheeseandcream.application.agent.query.FindAgentByIdQuery findAgentByIdQuery;

    public JpaFindAgentByIdQueryAdapter(com.lelouch.cheeseandcream.application.agent.query.FindAgentByIdQuery findAgentByIdQuery) {
        this.findAgentByIdQuery = findAgentByIdQuery;
    }

    @Override
    public Optional<Agent> findById(Long agentId) {
        return findAgentByIdQuery.findById(agentId);
    }
}
