package com.lelouch.cheeseandcream.infra.identificationtype;

import com.lelouch.cheeseandcream.application.identificationtype.IdentificationTypeUseCase;
import com.lelouch.cheeseandcream.application.identificationtype.dto.IdentificationTypeResponse;
import com.lelouch.cheeseandcream.application.identificationtype.dto.IdentificationTypeRequest;
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

    private final IdentificationTypeUseCase identificationTypeUseCase;

    public IdentityTypeRestController(IdentificationTypeUseCase identificationTypeUseCase) {
        this.identificationTypeUseCase = identificationTypeUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createDefaultIdentityTypes(@RequestBody IdentificationTypeRequest request) {
        identificationTypeUseCase.createIdentificationType(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IdentificationTypeResponse>> getAllIdentityTypes() {
        return new ResponseEntity<>(identificationTypeUseCase.getAllIdentificationTypes(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateIdentityType(@RequestBody IdentificationTypeRequest request, @PathVariable Long id) {
        identificationTypeUseCase.updateIdentificationType(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteIdentityType(@PathVariable Long id) {
        identificationTypeUseCase.deleteIdentificationType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
