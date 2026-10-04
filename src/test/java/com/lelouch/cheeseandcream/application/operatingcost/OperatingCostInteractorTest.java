package com.lelouch.cheeseandcream.application.operatingcost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.application.operatingcost.command.OperatingCostCommand;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostRequest;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import com.lelouch.cheeseandcream.application.operatingcost.query.OperatingCostQuery;
import com.lelouch.cheeseandcream.domain.OperatingCost;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class OperatingCostInteractorTest {

    private OperatingCostCommand command;
    private OperatingCostQuery query;
    private OperatingCostOutputPort outputPort;
    private OperatingCostInteractor interactor;

    @BeforeEach
    void setUp() {
        command = mock(OperatingCostCommand.class);
        query = mock(OperatingCostQuery.class);
        outputPort = mock(OperatingCostOutputPort.class);
        interactor = new OperatingCostInteractor(command, query, outputPort);
    }

    @Test
    void createsAnOperatingCostFromTheRequest() {
        interactor.createOperatingCost(new OperatingCostRequest("Rent", 900_000D));

        ArgumentCaptor<OperatingCost> captor = ArgumentCaptor.forClass(OperatingCost.class);
        verify(command).createOperatingCost(captor.capture());
        assertEquals("Rent", captor.getValue().getConcept());
        assertEquals(900_000D, captor.getValue().getAmount());
    }

    @Test
    void updatesAnOperatingCostUsingTheRequestedId() {
        interactor.updateOperatingCost(4L, new OperatingCostRequest("Electricity", 310_000D));

        ArgumentCaptor<OperatingCost> captor = ArgumentCaptor.forClass(OperatingCost.class);
        verify(command).updateOperatingCost(org.mockito.ArgumentMatchers.eq(4L), captor.capture());
        assertEquals("Electricity", captor.getValue().getConcept());
        assertEquals(310_000D, captor.getValue().getAmount());
    }

    @Test
    void delegatesDeletion() {
        interactor.deleteOperatingCost(7L);

        verify(command).deleteOperatingCost(7L);
    }

    @Test
    void mapsTheActiveOperatingCostPage() {
        Pageable pageable = PageRequest.of(1, 20, Sort.by("creationDate").descending());
        Page<OperatingCost> costs = new PageImpl<>(List.of(OperatingCost.create("Rent", 900_000D)));
        Page<OperatingCostResponse> responses = new PageImpl<>(List.of());
        when(query.getAllOperatingCost(pageable)).thenReturn(costs);
        when(outputPort.mapToResponse(costs)).thenReturn(responses);

        Page<OperatingCostResponse> result = interactor.getAllOperatingCost(pageable);

        assertSame(responses, result);
        verify(query).getAllOperatingCost(pageable);
        verify(outputPort).mapToResponse(costs);
    }

    @Test
    void mapsTheRequestedMonthAndYear() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("creationDate").descending());
        Page<OperatingCost> costs = new PageImpl<>(List.of());
        Page<OperatingCostResponse> responses = new PageImpl<>(List.of());
        when(query.getOperatingCostByMonth(pageable, 9, 2026)).thenReturn(costs);
        when(outputPort.mapToResponse(costs)).thenReturn(responses);

        Page<OperatingCostResponse> result = interactor.getOperatingCostByMonth(pageable, 9, 2026);

        assertSame(responses, result);
        verify(query).getOperatingCostByMonth(pageable, 9, 2026);
    }

    @Test
    void mapsSearchResultsForTheRequestedTerm() {
        Pageable pageable = PageRequest.of(2, 10, Sort.by("concept").ascending());
        OperatingCostTermRequest term = new OperatingCostTermRequest("rent");
        Page<OperatingCost> costs = new PageImpl<>(List.of());
        Page<OperatingCostResponse> responses = new PageImpl<>(List.of());
        when(query.searchOperatingCostByTerm(pageable, term)).thenReturn(costs);
        when(outputPort.mapToResponse(costs)).thenReturn(responses);

        Page<OperatingCostResponse> result = interactor.searchOperatingCostByTerm(pageable, term);

        assertSame(responses, result);
        verify(query).searchOperatingCostByTerm(pageable, term);
    }
}

