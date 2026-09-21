package com.lelouch.cheeseandcream.application.product;

import com.lelouch.cheeseandcream.application.product.dto.ProductRequest;
import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import com.lelouch.cheeseandcream.application.product.dto.ProductTermRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {

    void createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long productId);
    Page<ProductResponse> getProductsByAgentId(Long agentId, Pageable pageable);
    Page<ProductResponse> searchProducts(Long agentId, ProductTermRequest term, Pageable pageable);
    void updateProduct(Long productId, ProductRequest productRequest);
    void deleteProduct(Long productId);
}
