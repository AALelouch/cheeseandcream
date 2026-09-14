package com.lelouch.cheeseandcream.application.model.dashboard;

import java.time.LocalDateTime;

public record DashboardTimeRequest(LocalDateTime startTime, LocalDateTime endTime) {
}
