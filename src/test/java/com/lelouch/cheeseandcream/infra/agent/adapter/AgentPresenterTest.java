package com.lelouch.cheeseandcream.infra.agent.adapter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.Role;
import org.junit.jupiter.api.Test;

class AgentPresenterTest {

    private final AgentPresenter presenter = new AgentPresenter();

    @Test
    void mapsSeparatedBalancesAndRoleToTheResponseContract() {
        Agent agent = Agent.create(12L, "Ana Torres", "ana@example.com", "3001234567",
                "Calle 10", 1250000.0, 200000.0, Role.CLIENT, "1030123456", 1L);

        AgentResponse response = presenter.mapToResponse(agent);

        assertAll(
                () -> assertEquals("200000.0", response.payables()),
                () -> assertEquals("1250000.0", response.receivables()),
                () -> assertEquals("1050000.0", response.balance()),
                () -> assertEquals("CLIENT", response.role()),
                () -> assertEquals("1", response.identificationType()),
                () -> assertEquals("1030123456", response.identificationNumber()));
    }
}
