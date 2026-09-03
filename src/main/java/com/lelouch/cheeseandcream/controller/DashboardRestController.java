package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.DashBoardService;
import com.lelouch.cheeseandcream.model.dashboard.DashboardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final DashBoardService dashBoardService;

    public DashboardRestController(DashBoardService dashBoardService) {
        this.dashBoardService = dashBoardService;
    }

    /**
     * Gets complete financial metrics for a specific month.
     * Includes debt, revenue, profit, and pending balance.
     *
     * @param month Month (1-12)
     * @return DashboardResponse with all financial metrics
     */
    @GetMapping("/monthly/{month}")
    public ResponseEntity<DashboardResponse> getMonthlyDashboard(
            @org.springframework.web.bind.annotation.PathVariable int month) {
        DashboardResponse response = new DashboardResponse(
                dashBoardService.getTotalRevenueByMonth(month),
                dashBoardService.getTotalProfitByMonth(month),
                dashBoardService.getPendingBalanceByMonth(month)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Gets the total pending balance (Accounts Receivable) across all time.
     * Represents total amount customers still owe.
     *
     * @return Pending balance amount
     */
    @GetMapping("/pending-balance/total")
    public ResponseEntity<Double> getTotalPendingBalance() {
        return new ResponseEntity<>(dashBoardService.getTotalPendingBalance(), HttpStatus.OK);
    }

    /**
     * Gets the pending balance for a specific agent/customer.
     * Shows how much that customer still owes.
     *
     * @param agentId ID of the agent/customer
     * @return Pending balance for the agent
     */
    @GetMapping("/pending-balance/agent/{agentId}")
    public ResponseEntity<Double> getPendingBalanceByAgent(
            @org.springframework.web.bind.annotation.PathVariable Long agentId) {
        return new ResponseEntity<>(dashBoardService.getPendingBalanceByAgent(agentId), HttpStatus.OK);
    }

    /**
     * Gets pending balance for a specific month.
     * Represents unpaid sales for that month.
     *
     * @param month Month (1-12)
     * @return Pending balance for the month
     */
    @GetMapping("/pending-balance/monthly/{month}")
    public ResponseEntity<Double> getPendingBalanceByMonth(
            @org.springframework.web.bind.annotation.PathVariable int month) {
        return new ResponseEntity<>(dashBoardService.getPendingBalanceByMonth(month), HttpStatus.OK);
    }
}

