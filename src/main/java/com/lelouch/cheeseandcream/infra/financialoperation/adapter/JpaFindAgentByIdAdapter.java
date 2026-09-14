package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.FindAgentById;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.infra.orm.agent.AgentEntity;
import com.lelouch.cheeseandcream.infra.repository.AgentRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JpaFindAgentByIdAdapter implements FindAgentById {

    private final AgentRepository agentRepository;

    public JpaFindAgentByIdAdapter(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Optional<Agent> findById(Long agentId) {
        return agentRepository.findByIdAndActiveIsTrue(agentId).map(AgentEntity::toDomain);
    }
}
