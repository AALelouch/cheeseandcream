package com.lelouch.cheeseandcream.application.agent;

public record AgentRequest(String name, String email, String phoneNumber, String address, String balance, Long identificationTypeId, String identificationNumber) {
}
