package com.lelouch.cheeseandcream.application.dashboard;

import java.time.LocalDateTime;

public record DashboardTimeRequest(LocalDateTime startTime, LocalDateTime endTime) {
}
