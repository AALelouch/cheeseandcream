package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.DashBoardService;
import com.lelouch.cheeseandcream.model.dashboard.DashboardResponse;
import com.lelouch.cheeseandcream.model.dashboard.DashboardTimeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final DashBoardService dashBoardService;

    public DashboardRestController(DashBoardService dashBoardService) {
        this.dashBoardService = dashBoardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse response = new DashboardResponse(
                dashBoardService.getTotalDebt(),
                dashBoardService.getTotalRevenue(),
                dashBoardService.getTotalProfit()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/by-date-range")
    public ResponseEntity<DashboardResponse> getDashboardByDateRange(@RequestBody DashboardTimeRequest request) {
        DashboardResponse response = new DashboardResponse(
                dashBoardService.getTotalDebtByTimeRange(request),
                dashBoardService.getTotalRevenueByTimeRange(request),
                dashBoardService.getTotalProfitByTimeRange(request)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/total-debt")
    public ResponseEntity<Double> getTotalDebt() {
        return new ResponseEntity<>(dashBoardService.getTotalDebt(), HttpStatus.OK);
    }

    @PostMapping("/total-debt/by-date-range")
    public ResponseEntity<Double> getTotalDebtByDateRange(@RequestBody DashboardTimeRequest request) {
        return new ResponseEntity<>(dashBoardService.getTotalDebtByTimeRange(request), HttpStatus.OK);
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return new ResponseEntity<>(dashBoardService.getTotalRevenue(), HttpStatus.OK);
    }

    @PostMapping("/total-revenue/by-date-range")
    public ResponseEntity<Double> getTotalRevenueByDateRange(@RequestBody DashboardTimeRequest request) {
        return new ResponseEntity<>(dashBoardService.getTotalRevenueByTimeRange(request), HttpStatus.OK);
    }

    @GetMapping("/total-profit")
    public ResponseEntity<Double> getTotalProfit() {
        return new ResponseEntity<>(dashBoardService.getTotalProfit(), HttpStatus.OK);
    }

    @PostMapping("/total-profit/by-date-range")
    public ResponseEntity<Double> getTotalProfitByDateRange(@RequestBody DashboardTimeRequest request) {
        return new ResponseEntity<>(dashBoardService.getTotalProfitByTimeRange(request), HttpStatus.OK);
    }
}

