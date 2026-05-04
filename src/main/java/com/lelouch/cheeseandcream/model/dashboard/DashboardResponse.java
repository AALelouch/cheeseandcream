package com.lelouch.cheeseandcream.model.dashboard;

public record DashboardResponse(
        Double totalDebt,
        Double totalRevenue,
        Double totalProfit
) {
}

