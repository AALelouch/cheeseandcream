package com.lelouch.cheeseandcream.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lelouch.cheeseandcream.domain.FinancialOperation.OperationProduct;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FinancialOperationTest {

    @Test
    void saleWithProductsReducesInventoryAndIncreasesCustomerReceivablesUsingLineTotals() {
        Agent agent = clientWithReceivables(100.0);
        Product cheese = product(10L, 8.0, 7.0);
        Product cream = product(11L, 5.0, 4.0);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(), "Pedido", OperationType.SALE);

        operation.performProductBasedOperation(List.of(cheese, cream), Map.of(
                10L, OperationProduct.create(2.0, 15.0),
                11L, OperationProduct.create(3.0, 8.0)));

        assertEquals(54.0, operation.getTotal());
        assertEquals(6.0, cheese.getQuantity());
        assertEquals(2.0, cream.getQuantity());
        assertEquals(154.0, agent.getReceivables());
        assertEquals(0.0, agent.getPayables());
        assertEquals(154.0, agent.getBalance());
        assertEquals(2, operation.getOperationProducts().size());
    }

    @Test
    void purchaseWithProductsIncreasesInventoryAndSupplierPayables() {
        Agent agent = providerWithPayables(20.0);
        Product cheese = product(10L, 1.0, 7.0);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(), "Compra", OperationType.PURCHASE);

        operation.performProductBasedOperation(List.of(cheese),
                Map.of(10L, OperationProduct.create(4.0, 9.0)));

        assertEquals(5.0, cheese.getQuantity());
        assertEquals(36.0, operation.getTotal());
        assertEquals(56.0, agent.getPayables());
        assertEquals(0.0, agent.getReceivables());
        assertEquals(-56.0, agent.getBalance());
    }

    @Test
    void clientPaymentDecreasesReceivables() {
        Agent agent = clientWithReceivables(10.0);
        FinancialOperation clientPayment = FinancialOperation.create(agent, new ArrayList<>(), "", OperationType.CLIENT_PAYMENT);

        clientPayment.performSingleAmountOperation(5.0);

        assertEquals(5.0, agent.getReceivables());
        assertEquals(5.0, agent.getBalance());
    }

    @Test
    void supplierPaymentDecreasesPayables() {
        Agent agent = providerWithPayables(10.0);
        FinancialOperation payment = FinancialOperation.create(agent, new ArrayList<>(), "", OperationType.PAYMENT);

        payment.performSingleAmountOperation(5.0);

        assertEquals(5.0, agent.getPayables());
        assertEquals(-5.0, agent.getBalance());
    }

    @Test
    void singleAmountOperationRejectsZero() {
        FinancialOperation operation = FinancialOperation.create(clientWithReceivables(10.0),
                new ArrayList<>(), "", OperationType.SALE);

        assertThrows(BadRequestException.class, () -> operation.performSingleAmountOperation(0.0));
    }

    @Test
    void rejectsProductOperationWhenAnyRequestedProductIsMissing() {
        Agent agent = clientWithReceivables(10.0);
        Product cheese = product(10L, 5.0, 7.0);
        FinancialOperation operation = FinancialOperation.create(agent, new ArrayList<>(), "", OperationType.SALE);

        assertThrows(BadRequestException.class, () -> operation.performProductBasedOperation(
                List.of(cheese),
                Map.of(10L, OperationProduct.create(1.0, 9.0),
                        99L, OperationProduct.create(1.0, 12.0))));
        assertEquals(5.0, cheese.getQuantity());
        assertEquals(10.0, agent.getBalance());
    }

    private Agent clientWithReceivables(double receivables) {
        return Agent.create(1L, "Cliente", "cliente@example.com", "1", "Calle",
                receivables, 0.0, Role.CLIENT, "123", 1L);
    }

    private Agent providerWithPayables(double payables) {
        return Agent.create(2L, "Proveedor", "provider@example.com", "2", "Avenida",
                0.0, payables, Role.PROVIDER, "456", 2L);
    }

    private Product product(long id, double quantity, double cost) {
        return Product.create(id, "Producto", quantity, cost, "unit", null);
    }
}
