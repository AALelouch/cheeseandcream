package com.lelouch.cheeseandcream.infra.operatingcost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.operatingcost.OperatingCostUseCase;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostRequest;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class OperatingCostRestControllerTest {

    private OperatingCostUseCase useCase;
    private OperatingCostRestController controller;

    @BeforeEach
    void setUp() {
        useCase = mock(OperatingCostUseCase.class);
        controller = new OperatingCostRestController(useCase);
    }

    @Test
    void exposesCreateUpdateAndDeleteCommands() {
        OperatingCostRequest request = new OperatingCostRequest("Rent", 900_000D);

        assertEquals(HttpStatus.OK, controller.createOperatingCost(request).getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, controller.updateOperatingCost(request, 2L).getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, controller.deleteOperatingCost(2L).getStatusCode());

        verify(useCase).createOperatingCost(request);
        verify(useCase).updateOperatingCost(2L, request);
        verify(useCase).deleteOperatingCost(2L);
    }

    @Test
    void returnsTheListPageFromTheUseCase() {
        Page<OperatingCostResponse> page = new PageImpl<>(List.of());
        when(useCase.getAllOperatingCost(null)).thenReturn(page);

        ResponseEntity<Page<OperatingCostResponse>> response = controller.searchOperatingCostById(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(page, response.getBody());
    }

    @Test
    void returnsTheSearchPageFromTheUseCase() {
        OperatingCostTermRequest term = new OperatingCostTermRequest("rent");
        Page<OperatingCostResponse> page = new PageImpl<>(List.of());
        when(useCase.searchOperatingCostByTerm(null, term)).thenReturn(page);

        ResponseEntity<Page<OperatingCostResponse>> response = controller.searchOperatingCosts( term, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(page, response.getBody());
    }
}
