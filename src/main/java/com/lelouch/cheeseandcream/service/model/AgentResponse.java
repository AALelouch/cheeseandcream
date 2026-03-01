package com.lelouch.cheeseandcream.service.model;

public record AgentResponse(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String address
){
}
