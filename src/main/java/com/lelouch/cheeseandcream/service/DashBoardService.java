package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.service.model.dashboard.DashboardTimeRequest;

public interface DashBoardService {

        Double getTotalDebt();
        Double getTotalDebtByTimeRange(DashboardTimeRequest dashboardTimeRequest);
        Double getTotalRevenue();
        Double getTotalRevenueByTimeRange(DashboardTimeRequest dashboardTimeRequest);
        Double  getTotalProfit();
        Double getTotalProfitByTimeRange(DashboardTimeRequest dashboardTimeRequest);
}
