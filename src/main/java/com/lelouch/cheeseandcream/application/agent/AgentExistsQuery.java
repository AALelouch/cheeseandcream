package com.lelouch.cheeseandcream.application.agent;

public interface AgentExistsQuery {

    boolean existsWithSameUniqueData(String name, String email, String address, String identificationNumber);
    boolean existsWithSameUniqueDataExcludingId(String name, String email, String address, String identificationNumber, Long agentId);
}
