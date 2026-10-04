package com.lelouch.cheeseandcream.infra.operatingcost;

import com.lelouch.cheeseandcream.application.operatingcost.OperatingCostUseCase;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostRequest;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operating-cost")
public class OperatingCostRestController {

    private final OperatingCostUseCase operatingCostUseCase;

    public OperatingCostRestController(OperatingCostUseCase operatingCostUseCase) {
        this.operatingCostUseCase = operatingCostUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createOperatingCost(@RequestBody OperatingCostRequest request) {
        operatingCostUseCase.createOperatingCost(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateOperatingCost(@RequestBody OperatingCostRequest request,
    @PathVariable Long id) {
        operatingCostUseCase.updateOperatingCost(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOperatingCost(@PathVariable Long id) {
        operatingCostUseCase.deleteOperatingCost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<OperatingCostResponse>> searchOperatingCosts(@RequestBody OperatingCostTermRequest request, Pageable pageable) {
        return ResponseEntity.ok(operatingCostUseCase.searchOperatingCostByTerm(pageable, request));
    }

    @GetMapping()
    public ResponseEntity<Page<OperatingCostResponse>> searchOperatingCostById(Pageable pageable) {
        return ResponseEntity.ok(operatingCostUseCase.getAllOperatingCost(pageable));
    }

    @GetMapping("/month/{month}/{year}")
    public ResponseEntity<Page<OperatingCostResponse>> searchOperatingCostByMonth(@PathVariable int month, @PathVariable int year, Pageable pageable) {
        return ResponseEntity.ok(operatingCostUseCase.getOperatingCostByMonth(pageable, month, year));
    }

}
