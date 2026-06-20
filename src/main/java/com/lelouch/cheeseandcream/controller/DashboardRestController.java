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
}

