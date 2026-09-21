package com.lelouch.cheeseandcream.application.agent.dto;

public record AgentResponse(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String address,
    Double balance,
    String identificationType,
    String identificationNumber
){
}
