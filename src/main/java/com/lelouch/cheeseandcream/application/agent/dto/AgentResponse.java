package com.lelouch.cheeseandcream.application.agent.dto;

public record AgentResponse(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String address,
    String payables,
    String receivables,
    String balance,
    String role,
    String identificationType,
    String identificationNumber
){
}
