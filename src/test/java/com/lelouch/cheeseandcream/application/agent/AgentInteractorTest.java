package com.lelouch.cheeseandcream.application.agent;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.agent.command.SaveAgentCommand;
import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.query.AgentExistsQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.Role;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class AgentInteractorTest {

    @Test
    void createRejectsDuplicatedUniqueDataBeforeSaving() {
        AgentExistsQuery existsQuery = mock(AgentExistsQuery.class);
        SaveAgentCommand saveCommand = mock(SaveAgentCommand.class);
        AgentRequest request = new AgentRequest("Proveedor", "supplier@example.com", "1", "Calle",
                0.0, 0.0, 2L, Role.PROVIDER, "123");
        when(existsQuery.existsWithSameUniqueData("Proveedor", "supplier@example.com", "Calle", "123")).thenReturn(true);
        AgentInteractor interactor = new AgentInteractor(saveCommand, null, null, null, null, existsQuery, null);

        assertThrows(BadRequestException.class, () -> interactor.createAgent(request));

        verifyNoInteractions(saveCommand);
    }

    @Test
    void createMapsReceivablesPayablesAndRoleBeforeSaving() {
        AgentExistsQuery existsQuery = mock(AgentExistsQuery.class);
        SaveAgentCommand saveCommand = mock(SaveAgentCommand.class);
        AgentRequest request = new AgentRequest("Proveedor", "supplier@example.com", "1", "Calle",
                25.0, 80.0, 2L, Role.PROVIDER, "123");
        AgentInteractor interactor = new AgentInteractor(saveCommand, null, null, null, null, existsQuery, null);

        interactor.createAgent(request);

        ArgumentCaptor<Agent> captor = ArgumentCaptor.forClass(Agent.class);
        verify(saveCommand).save(captor.capture());
        Agent savedAgent = captor.getValue();
        assertAll(
                () -> assertEquals(25.0, savedAgent.getReceivables()),
                () -> assertEquals(80.0, savedAgent.getPayables()),
                () -> assertEquals(-55.0, savedAgent.getBalance()),
                () -> assertEquals(Role.PROVIDER, savedAgent.getRole()),
                () -> assertEquals(2L, savedAgent.getIdentificationTypeId()));
    }

}
