package com.lelouch.cheeseandcream.application.dashboard;

import com.lelouch.cheeseandcream.application.dashboard.dto.DashboardResponse;

public interface DashboardOutputPort {
    DashboardResponse present(Double revenue, Double profit, Double pendingBalance);
}
