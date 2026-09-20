package com.lelouch.cheeseandcream.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FinancialOperationTest {

    @Test
    void saleWithProductsReducesInventoryAndCustomerBalanceUsingLineTotals() {
        Agent agent = agentWithBalance(100.0);
        Product cheese = product(10L, 8.0, 15.0);
        Product cream = product(11L, 5.0, 8.0);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(), "Pedido", OperationType.SALE);

        operation.performProductBasedOperation(List.of(cheese, cream), Map.of(10L, 2.0, 11L, 3.0));

        assertEquals(54.0, operation.getTotal());
        assertEquals(6.0, cheese.getQuantity());
        assertEquals(2.0, cream.getQuantity());
        assertEquals(46.0, agent.getBalance());
        assertEquals(2, operation.getOperationProducts().size());
    }

    @Test
    void purchaseWithProductsIncreasesInventoryAndSupplierBalance() {
        Agent agent = agentWithBalance(20.0);
        Product cheese = product(10L, 1.0, 9.0);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(), "Compra", OperationType.PURCHASE);

        operation.performProductBasedOperation(List.of(cheese), Map.of(10L, 4.0));

        assertEquals(5.0, cheese.getQuantity());
        assertEquals(36.0, operation.getTotal());
        assertEquals(56.0, agent.getBalance());
    }

    @Test
    void singleAmountOperationsRejectZeroAndClientPaymentsIncreaseBalance() {
        FinancialOperation zeroAmount = FinancialOperation.create(agentWithBalance(10.0), new ArrayList<>(), "", OperationType.SALE);
        Agent agent = agentWithBalance(10.0);
        FinancialOperation clientPayment = FinancialOperation.create(agent, new ArrayList<>(), "", OperationType.CLIENT_PAYMENT);

        assertThrows(BadRequestException.class, () -> zeroAmount.performSingleAmountOperation(0.0));
        clientPayment.performSingleAmountOperation(5.0);
        assertEquals(15.0, agent.getBalance());
    }

    @Test
    void rejectsProductOperationWhenAnyRequestedProductIsMissing() {
        Agent agent = agentWithBalance(10.0);
        Product cheese = product(10L, 5.0, 9.0);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(), "", OperationType.SALE);

        assertThrows(BadRequestException.class,
                () -> operation.performProductBasedOperation(List.of(cheese), Map.of(10L, 1.0, 99L, 1.0)));
        assertEquals(5.0, cheese.getQuantity());
        assertEquals(10.0, agent.getBalance());
    }

    private Agent agentWithBalance(double balance) {
        return Agent.create(1L, "Cliente", "cliente@example.com", "1", "Calle", balance, "123");
    }

    private Product product(long id, double quantity, double price) {
        return Product.create(id, "Producto", quantity, price, price / 2, "unit", null);
    }
}
