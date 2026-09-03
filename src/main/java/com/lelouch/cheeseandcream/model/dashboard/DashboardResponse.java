package com.lelouch.cheeseandcream.model.dashboard;

/**
 * Complete dashboard financial metrics response.
 * Contains aggregated financial data for a specific period.
 */
public record DashboardResponse(
        Double totalRevenue,
        Double totalProfit,
        Double pendingBalance
) {
}

