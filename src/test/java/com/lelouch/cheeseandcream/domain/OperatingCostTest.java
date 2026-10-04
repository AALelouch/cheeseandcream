package com.lelouch.cheeseandcream.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class OperatingCostTest {

    @Test
    void createsANewOperatingCostFromInputValues() {
        OperatingCost cost = OperatingCost.create("Warehouse rent", 1_250_000D);

        assertNull(cost.getId());
        assertEquals("Warehouse rent", cost.getConcept());
        assertEquals(1_250_000D, cost.getAmount());
        assertNull(cost.getCreationDate());
    }

    @Test
    void rehydratesAnOperatingCostWithPersistenceMetadata() {
        LocalDateTime creationDate = LocalDateTime.of(2026, 9, 29, 8, 30);

        OperatingCost cost = OperatingCost.create("Utilities", 340_000D, 9L, creationDate);

        assertEquals(9L, cost.getId());
        assertEquals("Utilities", cost.getConcept());
        assertEquals(340_000D, cost.getAmount());
        assertEquals(creationDate, cost.getCreationDate());
    }
}

