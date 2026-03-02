package com.lelouch.cheeseandcream.service.model.agent;

public record AgentResponse(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String address,
    Double balance
){
}
