package com.lelouch.cheeseandcream.application.service;


public interface DashBoardService {

    /**
     * Gets total revenue for the specified month.
     * Calculated by summing all SALE operations.
     */
    Double getTotalRevenueByMonth(int month);

    /**
     * Gets total profit for the specified month.
     * Calculated by summing SALE and PAYMENT operations.
     */
    Double getTotalProfitByMonth(int month);

    /**
     * Gets pending balance (Accounts Receivable) for the specified month.
     * Represents unpaid sales = SUM(SALE) - SUM(PAYMENT)
     */
    Double getPendingBalanceByMonth(int month);

    /**
     * Gets total pending balance across all time.
     * Represents total amount customers still owe.
     */
    Double getTotalPendingBalance();

    /**
     * Gets pending balance for a specific agent/customer.
     * Shows how much that customer still owes.
     */
    Double getPendingBalanceByAgent(Long agentId);
}
