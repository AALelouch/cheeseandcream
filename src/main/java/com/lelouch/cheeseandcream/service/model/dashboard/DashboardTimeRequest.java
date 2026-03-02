package com.lelouch.cheeseandcream.service.model.dashboard;

import java.time.LocalDateTime;

public record DashboardTimeRequest(LocalDateTime startTime, LocalDateTime endTime) {
}
