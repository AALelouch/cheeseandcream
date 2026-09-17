package com.lelouch.cheeseandcream.application.dashboard;


public interface DashboardUseCase {
    DashboardResponse getMonthlyDashboard(int month);
    Double getPendingBalanceByMonth(int month);
    Double getTotalPendingBalance();
    Double getPendingBalanceByAgent(Long agentId);
}
