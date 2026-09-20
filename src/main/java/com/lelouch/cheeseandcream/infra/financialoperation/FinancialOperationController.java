package com.lelouch.cheeseandcream.infra.financialoperation;

import com.lelouch.cheeseandcream.application.financialoperation.FinancialOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.FinancialOperationResponse;
import com.lelouch.cheeseandcream.application.financialoperation.FinancialOperationTermRequest;
import com.lelouch.cheeseandcream.application.financialoperation.FinancialOperationUseCase;
import jakarta.validation.Valid;
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

    private final FinancialOperationUseCase financialOperationUseCase;

    public FinancialOperationController(FinancialOperationUseCase financialOperationUseCase) {
        this.financialOperationUseCase = financialOperationUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> addFinancialOperation(@RequestBody @Valid FinancialOperationRequest request) {
        financialOperationUseCase.addOperation(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/agent/{idAgent}")
    public ResponseEntity<Iterable<FinancialOperationResponse>> getFinancialOperationsByAgentId(@PathVariable Long idAgent, Pageable pageable) {
        return ResponseEntity.ok(financialOperationUseCase.getOperationsByAgentId(idAgent, pageable));
    }

    @PostMapping("/agent/{idAgent}/search")
    public ResponseEntity<Iterable<FinancialOperationResponse>> searchFinancialOperations(@PathVariable Long idAgent,
            @RequestBody FinancialOperationTermRequest term, Pageable pageable) {
        return ResponseEntity.ok(financialOperationUseCase.searchOperations(idAgent, term, pageable));
    }

}
