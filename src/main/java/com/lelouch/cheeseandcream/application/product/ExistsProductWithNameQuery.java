package com.lelouch.cheeseandcream.application.product;

public interface ExistsProductWithNameQuery {

    boolean existsByNameAndAgentId(String name, Long agentId);
    boolean existsByNameAndAgentIdExcludingId(String name, Long agentId, Long productId);
}
