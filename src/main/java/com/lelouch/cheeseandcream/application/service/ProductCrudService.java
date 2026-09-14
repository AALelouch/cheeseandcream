package com.lelouch.cheeseandcream.application.service;

import com.lelouch.cheeseandcream.application.model.product.ProductRequest;
import com.lelouch.cheeseandcream.application.model.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCrudService {

    void createProduct(ProductRequest productData);
    ProductResponse getProductById(Long productId);
    Page<ProductResponse> getProductsByAgentId(Long agentId, Pageable pageable);
    void updateProduct(Long productId, ProductRequest productData);
    void deleteProduct(Long productId);

}
