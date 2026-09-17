package com.lelouch.cheeseandcream.application.dashboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class DashboardInteractor implements DashboardUseCase {

    private final FinancialMetricsQuery financialMetricsQuery;
    private final AgentBalanceQuery agentBalanceQuery;
    private final DashboardOutputPort dashboardOutputPort;

    public DashboardInteractor(FinancialMetricsQuery financialMetricsQuery,
            AgentBalanceQuery agentBalanceQuery, DashboardOutputPort dashboardOutputPort) {
        this.financialMetricsQuery = financialMetricsQuery;
        this.agentBalanceQuery = agentBalanceQuery;
        this.dashboardOutputPort = dashboardOutputPort;
    }

    @Override
    public DashboardResponse getMonthlyDashboard(int month) {
        DateRange range = rangeFor(month);
        return dashboardOutputPort.present(
                zeroIfNull(financialMetricsQuery.getRevenue(range.start(), range.end())),
                zeroIfNull(financialMetricsQuery.getProfit(range.start(), range.end())),
                zeroIfNull(financialMetricsQuery.getPendingBalance(range.start(), range.end())));
    }

    @Override
    public Double getPendingBalanceByMonth(int month) {
        DateRange range = rangeFor(month);
        return zeroIfNull(financialMetricsQuery.getPendingBalance(range.start(), range.end()));
    }

    @Override
    public Double getTotalPendingBalance() {
        return zeroIfNull(agentBalanceQuery.getTotalPendingBalance());
    }

    @Override
    public Double getPendingBalanceByAgent(Long agentId) {
        return zeroIfNull(agentBalanceQuery.getPendingBalanceByAgent(agentId));
    }

    private DateRange rangeFor(int month) {
        LocalDateTime start = LocalDateTime.of(LocalDate.now().getYear(), month, 1, 0, 0);
        return new DateRange(start, start.plusMonths(1));
    }

    private Double zeroIfNull(Double value) {
        return value == null ? 0.0 : value;
    }

    private record DateRange(LocalDateTime start, LocalDateTime end) {
    }
}
