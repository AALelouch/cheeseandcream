package com.lelouch.cheeseandcream.application.dashboard;

public interface AgentBalanceQuery {
    Double getTotalPendingBalance();
    Double getPendingBalanceByAgent(Long agentId);
}
