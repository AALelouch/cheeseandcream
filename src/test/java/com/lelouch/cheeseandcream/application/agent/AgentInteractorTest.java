package com.lelouch.cheeseandcream.application.agent;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.agent.command.SaveAgentCommand;
import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.application.agent.query.AgentExistsQuery;
import com.lelouch.cheeseandcream.application.agent.query.FindAgentsWithProductsQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class AgentInteractorTest {

    @Test
    void createRejectsDuplicatedUniqueDataBeforeSaving() {
        AgentExistsQuery existsQuery = mock(AgentExistsQuery.class);
        SaveAgentCommand saveCommand = mock(SaveAgentCommand.class);
        AgentRequest request = new AgentRequest("Proveedor", "supplier@example.com", "1", "Calle", "0", 2L, "123");
        when(existsQuery.existsWithSameUniqueData("Proveedor", "supplier@example.com", "Calle", "123")).thenReturn(true);
        AgentInteractor interactor = new AgentInteractor(saveCommand, null, null, null, null, null, existsQuery, null, null);

        assertThrows(BadRequestException.class, () -> interactor.createAgent(request));

        org.mockito.Mockito.verifyNoInteractions(saveCommand);
    }

    @Test
    void createRejectsAnInvalidBalanceBeforeSaving() {
        AgentExistsQuery existsQuery = mock(AgentExistsQuery.class);
        SaveAgentCommand saveCommand = mock(SaveAgentCommand.class);
        AgentRequest request = new AgentRequest("Proveedor", "supplier@example.com", "1", "Calle", "not-a-number", 2L, "123");
        AgentInteractor interactor = new AgentInteractor(saveCommand, null, null, null, null, null, existsQuery, null, null);

        assertThrows(BadRequestException.class, () -> interactor.createAgent(request));

        org.mockito.Mockito.verifyNoInteractions(saveCommand);
    }

    @Test
    void getAgentsWithProductsUsesTheDedicatedQuery() {
        FindAgentsWithProductsQuery query = mock(FindAgentsWithProductsQuery.class);
        AgentOutputPort outputPort = mock(AgentOutputPort.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Agent> agents = new PageImpl<>(java.util.List.of(
                Agent.create(7L, "Supplier", "supplier@example.com", "123", "Address", 0.0, "900")));
        Page<AgentResponse> responses = new PageImpl<>(java.util.List.of(
                new AgentResponse(7L, "Supplier", "supplier@example.com", "123", "Address", 0.0, null, "900")));
        when(query.findAgentsWithProducts(pageable)).thenReturn(agents);
        when(outputPort.mapToResponse(agents)).thenReturn(responses);
        AgentInteractor interactor = new AgentInteractor(null, null, null, null, query, null, null, null, outputPort);

        Page<AgentResponse> result = interactor.getAgentsWithProducts(pageable);

        assertSame(responses, result);
        verify(query).findAgentsWithProducts(pageable);
    }
}
