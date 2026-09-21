package com.lelouch.cheeseandcream.application.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lelouch.cheeseandcream.application.dashboard.dto.DashboardResponse;
import com.lelouch.cheeseandcream.application.dashboard.query.AgentBalanceQuery;
import com.lelouch.cheeseandcream.application.dashboard.query.FinancialMetricsQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DashboardInteractorTest {

    @Test
    void monthlyDashboardUsesRequestedMonthAndNormalizesNullMetrics() {
        RecordingFinancialQuery financialQuery = new RecordingFinancialQuery();
        DashboardInteractor interactor = new DashboardInteractor(financialQuery, new StubAgentBalanceQuery(),
                DashboardResponse::new);

        DashboardResponse response = interactor.getMonthlyDashboard(4);

        assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 4, 1, 0, 0), financialQuery.start);
        assertEquals(financialQuery.start.plusMonths(1), financialQuery.end);
        assertEquals(120.0, response.totalRevenue());
        assertEquals(0.0, response.totalProfit());
        assertEquals(35.0, response.pendingBalance());
    }

    private static final class RecordingFinancialQuery implements FinancialMetricsQuery {
        private LocalDateTime start;
        private LocalDateTime end;

        @Override
        public Double getRevenue(LocalDateTime startDate, LocalDateTime endDate) {
            recordRange(startDate, endDate);
            return 120.0;
        }

        @Override
        public Double getProfit(LocalDateTime startDate, LocalDateTime endDate) {
            recordRange(startDate, endDate);
            return null;
        }

        @Override
        public Double getPendingBalance(LocalDateTime startDate, LocalDateTime endDate) {
            recordRange(startDate, endDate);
            return 35.0;
        }

        private void recordRange(LocalDateTime startDate, LocalDateTime endDate) {
            this.start = startDate;
            this.end = endDate;
        }
    }

    private static final class StubAgentBalanceQuery implements AgentBalanceQuery {
        @Override
        public Double getTotalPendingBalance() {
            return 0.0;
        }

        @Override
        public Double getPendingBalanceByAgent(Long agentId) {
            return 0.0;
        }
    }
}
