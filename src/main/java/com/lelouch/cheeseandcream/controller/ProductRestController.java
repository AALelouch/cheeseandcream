package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.ProductCrudService;
import com.lelouch.cheeseandcream.service.model.product.ProductRequest;
import com.lelouch.cheeseandcream.service.model.product.ProductResponse;
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
    
    private final ProductCrudService productCrudService;

    public ProductRestController(ProductCrudService productCrudService) {
        this.productCrudService = productCrudService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest ProductRequest) {
        productCrudService.createProduct(ProductRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductRequest ProductRequest, @PathVariable Long id) {
        productCrudService.updateProduct(id, ProductRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productCrudService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productCrudService.getAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productCrudService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
