package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.repository.FinancialOperationRepository;
import com.lelouch.cheeseandcream.service.DashBoardService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    private final FinancialOperationRepository financialOperationRepository;
    private final AgentRepository agentRepository;

    public DashBoardServiceImpl(FinancialOperationRepository financialOperationRepository,
                               AgentRepository agentRepository) {
        this.financialOperationRepository = financialOperationRepository;
        this.agentRepository = agentRepository;
    }

    /**
     * Gets the total revenue for the specified month.
     * Revenue is calculated by summing all SALE operations.
     *
     * @param month Month (1-12)
     * @return Total revenue for the month, or 0.0 if no records
     */
    @Override
    public Double getTotalRevenueByMonth(int month) {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), month, 1, 0, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        Double revenue = financialOperationRepository.sumRevenueByTimeRange(startDate, endDate);
        return revenue != null ? revenue : 0.0;
    }

    /**
     * Gets the total profit for the specified month.
     * Profit is calculated by deducting product costs from sales.
     *
     * @param month Month (1-12)
     * @return Total profit for the month, or 0.0 if no records
     */
    @Override
    public Double getTotalProfitByMonth(int month) {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), month, 1, 0, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        Double profit = financialOperationRepository.sumProfitByTimeRange(startDate, endDate);
        return profit != null ? profit : 0.0;
    }

    /**
     * Gets the pending balance (Accounts Receivable) for the specified month.
     * Reads from Agent balance field.
     *
     * @param month Month (1-12)
     * @return Pending balance for the month
     */
    @Override
    public Double getPendingBalanceByMonth(int month) {
        // Note: Returns sum of all agents' balance for current logic
        // This queries all active agents regardless of month
        Double pendingBalance = agentRepository.getTotalPendingBalance();
        return pendingBalance != null ? pendingBalance : 0.0;
    }

    /**
     * Gets the total pending balance (Accounts Receivable) across all time.
     * Represents the total amount customers still owe.
     *
     * @return Total pending balance (total Accounts Receivable)
     */
    @Override
    public Double getTotalPendingBalance() {
        Double totalPendingBalance = agentRepository.getTotalPendingBalance();
        return totalPendingBalance != null ? totalPendingBalance : 0.0;
    }

    /**
     * Gets the pending balance for a specific agent/customer.
     * Returns the agent's balance field directly.
     *
     * @param agentId ID of the agent/customer
     * @return Pending balance for the agent
     */
    @Override
    public Double getPendingBalanceByAgent(Long agentId) {
        Double agentPendingBalance = agentRepository.getPendingBalanceByAgent(agentId);
        return agentPendingBalance != null ? agentPendingBalance : 0.0;
    }
}
