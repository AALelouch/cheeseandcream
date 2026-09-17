package com.lelouch.cheeseandcream.application.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {

    void createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long productId);
    Page<ProductResponse> getProductsByAgentId(Long agentId, Pageable pageable);
    void updateProduct(Long productId, ProductRequest productRequest);
    void deleteProduct(Long productId);
}
