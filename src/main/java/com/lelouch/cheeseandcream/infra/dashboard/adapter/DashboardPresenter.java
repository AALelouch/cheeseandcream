package com.lelouch.cheeseandcream.infra.dashboard.adapter;

import com.lelouch.cheeseandcream.application.dashboard.DashboardOutputPort;
import com.lelouch.cheeseandcream.application.dashboard.dto.DashboardResponse;
import org.springframework.stereotype.Service;

@Service
public class DashboardPresenter implements DashboardOutputPort {

    @Override
    public DashboardResponse present(Double revenue, Double profit, Double pendingBalance) {
        return new DashboardResponse(revenue, profit, pendingBalance);
    }
}
