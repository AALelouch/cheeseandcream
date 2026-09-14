package com.lelouch.cheeseandcream.application.model.agent;

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
