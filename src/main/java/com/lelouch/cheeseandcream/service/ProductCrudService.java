package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.product.ProductRequest;
import com.lelouch.cheeseandcream.model.product.ProductResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCrudService {

    void createProduct(ProductRequest productData);
    ProductResponse getProductById(Long productId);
    Page<ProductResponse> getProductsByAgentId(Long agentId, Pageable pageable);
    void updateProduct(Long productId, ProductRequest productData);
    void deleteProduct(Long productId);

}
