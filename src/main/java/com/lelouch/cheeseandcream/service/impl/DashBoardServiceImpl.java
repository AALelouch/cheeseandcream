package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.service.DashBoardService;
import com.lelouch.cheeseandcream.model.dashboard.DashboardTimeRequest;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    private final AgentRepository agentRepository;

    public DashBoardServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Double getTotalDebt() {
        Double totalDebt = 0.0;
        return agentRepository.findAll().stream().map(agent -> {
            if (agent.getBalance() != null && agent.getBalance() > 0) {
                return agent.getBalance();
            } else {
                return 0.0;
            }
        }).reduce(totalDebt, Double::sum);
    }

    @Override
    public Double getTotalDebtByTimeRange(DashboardTimeRequest dashboardTimeRequest) {
        Double totalDebt = 0.0;
        return agentRepository.findByModifiedDateBetween(dashboardTimeRequest.startTime(), dashboardTimeRequest.endTime()).stream().map(agent -> {
            if (agent.getBalance() != null && agent.getBalance() > 0) {
                return agent.getBalance();
            } else {
                return 0.0;
            }
        }).reduce(totalDebt, Double::sum);
    }

    @Override
    public Double getTotalRevenue() {
        return 0.0;
    }

    @Override
    public Double getTotalRevenueByTimeRange(DashboardTimeRequest dashboardTimeRequest) {
        return 0.0;
    }

    @Override
    public Double getTotalProfit() {
        return 0.0;

    }

    @Override
    public Double getTotalProfitByTimeRange(DashboardTimeRequest dashboardTimeRequest) {
        return 0.0;
    }
}
