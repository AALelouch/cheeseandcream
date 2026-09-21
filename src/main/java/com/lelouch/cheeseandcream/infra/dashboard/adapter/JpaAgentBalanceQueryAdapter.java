package com.lelouch.cheeseandcream.infra.dashboard.adapter;

import com.lelouch.cheeseandcream.application.dashboard.query.AgentBalanceQuery;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentRepository;
import org.springframework.stereotype.Service;

@Service
public class JpaAgentBalanceQueryAdapter implements AgentBalanceQuery {

    private final AgentRepository agentRepository;

    public JpaAgentBalanceQueryAdapter(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Double getTotalPendingBalance() {
        return agentRepository.getTotalPendingBalance();
    }

    @Override
    public Double getPendingBalanceByAgent(Long agentId) {
        return agentRepository.getPendingBalanceByAgent(agentId);
    }
}
