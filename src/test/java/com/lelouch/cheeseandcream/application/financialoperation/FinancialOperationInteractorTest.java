package com.lelouch.cheeseandcream.application.financialoperation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.financialoperation.command.SaveFinancialOperationCommand;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationRequest.ProductOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationResponse;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationTermRequest;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.SearchFinancialOperationsByTermQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.OperationType;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.domain.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class FinancialOperationInteractorTest {

    @Test
    void addSingleAmountSalePersistsTheOperationAndIncreasesReceivables() {
        Agent agent = clientWithReceivables(100.0);
        FindAgentByIdQuery findAgent = id -> Optional.of(agent);
        RecordingSaveCommand saveCommand = new RecordingSaveCommand();
        FinancialOperationInteractor interactor = new FinancialOperationInteractor(saveCommand, findAgent, null, null, null, null, null);
        FinancialOperationRequest request = new FinancialOperationRequest(new HashMap<>(), 5L, 40.0, "Factura", OperationType.SALE);

        interactor.addOperation(request);

        assertEquals(140.0, agent.getReceivables());
        assertEquals(140.0, agent.getBalance());
        assertEquals(40.0, saveCommand.saved.getTotal());
        assertEquals(OperationType.SALE, saveCommand.saved.getOperationType());
    }

    @Test
    void addSaleWithProductsAndAmountPersistsSaleAndClientPayment() {
        Agent agent = clientWithReceivables(100.0);
        Product product = Product.create(9L, "Queso", 5.0, 7.0, "unit", null);
        RecordingSaveCommand saveCommand = new RecordingSaveCommand();
        FinancialOperationInteractor interactor = new FinancialOperationInteractor(saveCommand,
                id -> Optional.of(agent), ids -> List.of(product), null, null, null, null);
        HashMap<Long, ProductOperationRequest> products = new HashMap<>();
        products.put(9L, new ProductOperationRequest(2.0, 15.0));
        FinancialOperationRequest request = new FinancialOperationRequest(
                products, 5L, 10.0, "Factura", OperationType.SALE);

        interactor.addOperation(request);

        assertEquals(2, saveCommand.savedOperations.size());
        assertEquals(OperationType.SALE, saveCommand.savedOperations.get(0).getOperationType());
        assertEquals(30.0, saveCommand.savedOperations.get(0).getTotal());
        assertEquals(OperationType.CLIENT_PAYMENT, saveCommand.savedOperations.get(1).getOperationType());
        assertEquals(10.0, saveCommand.savedOperations.get(1).getTotal());
        assertEquals(3.0, product.getQuantity());
        assertEquals(120.0, agent.getReceivables());
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
        FinancialOperationInteractor interactor = new FinancialOperationInteractor(null, null, null, null, query, null,
                outputPort);

        Page<FinancialOperationResponse> result = interactor.searchOperations(15L, term, pageable);

        assertSame(responses, result);
        verify(query).searchByTerm(15L, term, pageable);
    }

    private Agent clientWithReceivables(double receivables) {
        return Agent.create(5L, "Cliente", "c@example.com", "1", "Calle",
                receivables, 0.0, Role.CLIENT, "123", 1L);
    }

    private static final class RecordingSaveCommand implements SaveFinancialOperationCommand {
        private FinancialOperation saved;
        private final List<FinancialOperation> savedOperations = new ArrayList<>();

        @Override
        public void save(FinancialOperation financialOperation) {
            saved = financialOperation;
            savedOperations.add(financialOperation);
        }
    }
}
