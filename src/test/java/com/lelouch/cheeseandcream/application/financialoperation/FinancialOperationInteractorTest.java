package com.lelouch.cheeseandcream.application.financialoperation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.financialoperation.command.SaveFinancialOperationCommand;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationResponse;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationTermRequest;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.SearchFinancialOperationsByTermQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.OperationType;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class FinancialOperationInteractorTest {

    @Test
    void addSingleAmountSalePersistsTheOperationAndUpdatesTheAgentBalance() {
        Agent agent = Agent.create(5L, "Cliente", "c@example.com", "1", "Calle", 100.0, "123");
        FindAgentByIdQuery findAgent = id -> Optional.of(agent);
        RecordingSaveCommand saveCommand = new RecordingSaveCommand();
        FinancialOperationInteractor interactor = new FinancialOperationInteractor(saveCommand, findAgent, null, null, null, null);
        FinancialOperationRequest request = new FinancialOperationRequest(new HashMap<>(), 5L, 40.0, "Factura", OperationType.SALE);

        interactor.addOperation(request);

        assertEquals(60.0, agent.getBalance());
        assertEquals(40.0, saveCommand.saved.getTotal());
        assertEquals(OperationType.SALE, saveCommand.saved.getOperationType());
    }

    @Test
    void addOperationRejectsAnAmountAlongsideProductsBeforePersisting() {
        Agent agent = Agent.create(5L, "Cliente", "c@example.com", "1", "Calle", 100.0, "123");
        RecordingSaveCommand saveCommand = new RecordingSaveCommand();
        FinancialOperationInteractor interactor = new FinancialOperationInteractor(saveCommand, id -> Optional.of(agent),
                ids -> java.util.List.of(), null, null, null);
        HashMap<Long, Double> products = new HashMap<>();
        products.put(9L, 1.0);
        FinancialOperationRequest request = new FinancialOperationRequest(products, 5L, 1.0, "Factura", OperationType.SALE);

        assertThrows(BadRequestException.class, () -> interactor.addOperation(request));
        assertEquals(null, saveCommand.saved);
        assertEquals(100.0, agent.getBalance());
    }

    @Test
    void searchOperationsKeepsTheSelectedAgentScope() {
        SearchFinancialOperationsByTermQuery query = mock(SearchFinancialOperationsByTermQuery.class);
        FinancialOperationOutputPort outputPort = mock(FinancialOperationOutputPort.class);
        FinancialOperationTermRequest term = new FinancialOperationTermRequest("invoice");
        Pageable pageable = PageRequest.of(0, 20);
        Page<FinancialOperation> operations = new PageImpl<>(java.util.List.of());
        Page<FinancialOperationResponse> responses = new PageImpl<>(java.util.List.of());
        when(query.searchByTerm(15L, term, pageable)).thenReturn(operations);
        when(outputPort.mapToResponse(operations)).thenReturn(responses);
        FinancialOperationInteractor interactor = new FinancialOperationInteractor(null, null, null, null, query,
                outputPort);

        Page<FinancialOperationResponse> result = interactor.searchOperations(15L, term, pageable);

        assertSame(responses, result);
        verify(query).searchByTerm(15L, term, pageable);
    }

    private static final class RecordingSaveCommand implements SaveFinancialOperationCommand {
        private FinancialOperation saved;

        @Override
        public void save(FinancialOperation financialOperation) {
            saved = financialOperation;
        }
    }
}
