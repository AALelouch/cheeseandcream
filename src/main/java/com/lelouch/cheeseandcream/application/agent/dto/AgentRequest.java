package com.lelouch.cheeseandcream.application.agent.dto;

import com.lelouch.cheeseandcream.domain.Role;

public record AgentRequest(String name, String email, String phoneNumber, String address,
                           Double receivables, Double payables, Long identificationTypeId, Role role,
                           String identificationNumber) {
}
