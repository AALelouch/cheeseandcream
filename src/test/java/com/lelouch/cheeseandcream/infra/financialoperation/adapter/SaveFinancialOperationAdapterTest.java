package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.OperationType;
import com.lelouch.cheeseandcream.domain.Role;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentEntity;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentRepository;
import com.lelouch.cheeseandcream.infra.financialoperation.persistence.FinancialOperationEntity;
import com.lelouch.cheeseandcream.infra.financialoperation.persistence.FinancialOperationRepository;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class SaveFinancialOperationAdapterTest {

    @Test
    void saveCopiesReceivablesAndPayablesToTheManagedAgent() {
        FinancialOperationRepository financialOperationRepository = mock(FinancialOperationRepository.class);
        AgentRepository agentRepository = mock(AgentRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        AgentEntity managedAgent = new AgentEntity();
        Agent agent = Agent.create(5L, "Cliente", "client@example.com", "1", "Calle",
                100.0, 15.0, Role.CLIENT, "123", 1L);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(),
                "Factura", OperationType.SALE);
        operation.performSingleAmountOperation(40.0);
        when(agentRepository.findById(5L)).thenReturn(Optional.of(managedAgent));
        when(productRepository.findAllByIdInAndActiveIsTrue(List.of())).thenReturn(List.of());
        SaveFinancialOperationAdapter adapter = new SaveFinancialOperationAdapter(financialOperationRepository,
                agentRepository, productRepository);

        adapter.save(operation);

        ArgumentCaptor<FinancialOperationEntity> captor = ArgumentCaptor.forClass(FinancialOperationEntity.class);
        verify(financialOperationRepository).save(captor.capture());
        FinancialOperationEntity savedOperation = captor.getValue();
        assertAll(
                () -> assertEquals(140.0, managedAgent.getReceivables()),
                () -> assertEquals(15.0, managedAgent.getPayables()),
                () -> assertSame(managedAgent, savedOperation.getAgentEntity()),
                () -> assertEquals(40.0, savedOperation.getTotal()),
                () -> assertEquals(OperationType.SALE, savedOperation.getOperationType()));
    }
}
