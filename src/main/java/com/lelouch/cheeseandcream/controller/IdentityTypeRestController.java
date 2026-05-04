package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.IdentificationTypeCrudService;
import com.lelouch.cheeseandcream.model.agent.IdentificationTypeResponse;
import com.lelouch.cheeseandcream.model.agent.IdentificationTypeRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/identity-types")
public class IdentityTypeRestController {

    private final IdentificationTypeCrudService identificationTypeCrudService;

    public IdentityTypeRestController(IdentificationTypeCrudService identificationTypeCrudService) {
        this.identificationTypeCrudService = identificationTypeCrudService;
    }

    @PostMapping
    public ResponseEntity<Void> createDefaultIdentityTypes(@RequestBody IdentificationTypeRequest request) {
        identificationTypeCrudService.createIdentificationType(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IdentificationTypeResponse>> getAllIdentityTypes() {
        return new ResponseEntity<>(identificationTypeCrudService.getAllIdentificationTypes(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateIdentityType(@RequestBody IdentificationTypeRequest request, @PathVariable Long id) {
        identificationTypeCrudService.updateIdentificationType(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteIdentityType(@PathVariable Long id) {
        identificationTypeCrudService.deleteIdentificationType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
