package com.lelouch.cheeseandcream.infra.dashboard.adapter;

import com.lelouch.cheeseandcream.application.dashboard.FinancialMetricsQuery;
import com.lelouch.cheeseandcream.infra.financialoperation.FinancialOperationRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class JpaFinancialMetricsQueryAdapter implements FinancialMetricsQuery {

    private final FinancialOperationRepository financialOperationRepository;

    public JpaFinancialMetricsQueryAdapter(FinancialOperationRepository financialOperationRepository) {
        this.financialOperationRepository = financialOperationRepository;
    }

    @Override
    public Double getRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return financialOperationRepository.sumRevenueByTimeRange(startDate, endDate);
    }

    @Override
    public Double getProfit(LocalDateTime startDate, LocalDateTime endDate) {
        return financialOperationRepository.sumProfitByTimeRange(startDate, endDate);
    }

    @Override
    public Double getPendingBalance(LocalDateTime startDate, LocalDateTime endDate) {
        return financialOperationRepository.sumPendingBalanceByTimeRange(startDate, endDate);
    }
}
