package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.model.operation.FinancialOperationRequest;
import com.lelouch.cheeseandcream.model.operation.FinancialOperationResponse;
import com.lelouch.cheeseandcream.service.FinancialOperationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financial-operations")
public class FinancialOperationController {

    private final FinancialOperationService financialOperationService;

    public FinancialOperationController(FinancialOperationService financialOperationService) {
        this.financialOperationService = financialOperationService;
    }

    @PostMapping
    public ResponseEntity<Void> addFinancialOperation(@RequestBody FinancialOperationRequest request) {
        financialOperationService.addOperation(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/agent/{idAgent}")
    public ResponseEntity<Iterable<FinancialOperationResponse>> getFinancialOperationsByAgentId(@PathVariable Long idAgent, Pageable pageable) {
        return ResponseEntity.ok(financialOperationService.getOperationsByAgentId(idAgent, pageable));
    }

}
