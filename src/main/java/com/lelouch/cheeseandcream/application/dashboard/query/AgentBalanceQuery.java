package com.lelouch.cheeseandcream.application.dashboard.query;

public interface AgentBalanceQuery {
    Double getTotalPendingBalance();
    Double getPendingBalanceByAgent(Long agentId);
}
