package com.lelouch.cheeseandcream.application.dashboard.dto;

import java.time.LocalDateTime;

public record DashboardTimeRequest(LocalDateTime startTime, LocalDateTime endTime) {
}
