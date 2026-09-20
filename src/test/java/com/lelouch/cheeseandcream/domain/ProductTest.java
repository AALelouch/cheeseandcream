package com.lelouch.cheeseandcream.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void adjustsInventoryForValidAmounts() {
        Product product = productWithQuantity(10.0);

        product.increaseQuantity(4.5);
        product.decreaseQuantity(6.0);

        assertEquals(8.5, product.getQuantity());
    }

    @Test
    void rejectsNonPositiveAmountsAndInventoryBelowZero() {
        Product product = productWithQuantity(3.0);

        assertThrows(BadRequestException.class, () -> product.increaseQuantity(0.0));
        assertThrows(BadRequestException.class, () -> product.decreaseQuantity(-1.0));
        assertThrows(BadRequestException.class, () -> product.decreaseQuantity(3.1));
        assertEquals(3.0, product.getQuantity());
    }

    private Product productWithQuantity(double quantity) {
        return Product.create(1L, "Queso", quantity, 12.0, 7.0, "unit", null);
    }
}
