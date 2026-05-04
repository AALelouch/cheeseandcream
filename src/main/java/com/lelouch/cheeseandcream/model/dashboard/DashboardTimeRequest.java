package com.lelouch.cheeseandcream.model.dashboard;

import java.time.LocalDateTime;

public record DashboardTimeRequest(LocalDateTime startTime, LocalDateTime endTime) {
}
