package com.lelouch.cheeseandcream.application.dashboard;

import java.time.LocalDateTime;

public interface FinancialMetricsQuery {
    Double getRevenue(LocalDateTime startDate, LocalDateTime endDate);
    Double getProfit(LocalDateTime startDate, LocalDateTime endDate);
    Double getPendingBalance(LocalDateTime startDate, LocalDateTime endDate);
}
