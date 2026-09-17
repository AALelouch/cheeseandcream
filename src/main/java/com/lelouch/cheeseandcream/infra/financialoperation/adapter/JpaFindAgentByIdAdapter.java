package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.FindAgentById;
import com.lelouch.cheeseandcream.application.agent.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JpaFindAgentByIdAdapter implements FindAgentById {

    private final FindAgentByIdQuery findAgentByIdQuery;

    public JpaFindAgentByIdAdapter(FindAgentByIdQuery findAgentByIdQuery) {
        this.findAgentByIdQuery = findAgentByIdQuery;
    }

    @Override
    public Optional<Agent> findById(Long agentId) {
        return findAgentByIdQuery.findById(agentId);
    }
}
