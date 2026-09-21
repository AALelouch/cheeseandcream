package com.lelouch.cheeseandcream.application.dashboard;

import com.lelouch.cheeseandcream.application.dashboard.dto.DashboardResponse;

public interface DashboardUseCase {
    DashboardResponse getMonthlyDashboard(int month);
    Double getPendingBalanceByMonth(int month);
    Double getTotalPendingBalance();
    Double getPendingBalanceByAgent(Long agentId);
}
