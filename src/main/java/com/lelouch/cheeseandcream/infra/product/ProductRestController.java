package com.lelouch.cheeseandcream.infra.product;

import com.lelouch.cheeseandcream.application.product.ProductUseCase;
import com.lelouch.cheeseandcream.application.product.ProductRequest;
import com.lelouch.cheeseandcream.application.product.ProductResponse;
import com.lelouch.cheeseandcream.application.product.ProductTermRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/products")
public class ProductRestController {
    
    private final ProductUseCase productUseCase;

    public ProductRestController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest ProductRequest) {
        productUseCase.createProduct(ProductRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductRequest ProductRequest, @PathVariable Long id) {
        productUseCase.updateProduct(id, ProductRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productUseCase.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<Iterable<ProductResponse>> getProductsByAgentId(@PathVariable Long agentId, Pageable pageable) {
        return new ResponseEntity<>(productUseCase.getProductsByAgentId(agentId, pageable), HttpStatus.OK);
    }

    @PostMapping("/agent/{agentId}/search")
    public ResponseEntity<Iterable<ProductResponse>> searchProducts(@PathVariable Long agentId,
            @RequestBody ProductTermRequest term, Pageable pageable) {
        return new ResponseEntity<>(productUseCase.searchProducts(agentId, term, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
