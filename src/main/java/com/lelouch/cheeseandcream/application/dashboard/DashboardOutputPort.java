package com.lelouch.cheeseandcream.application.dashboard;


public interface DashboardOutputPort {
    DashboardResponse present(Double revenue, Double profit, Double pendingBalance);
}
