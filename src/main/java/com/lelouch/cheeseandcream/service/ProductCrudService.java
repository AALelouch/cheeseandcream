package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.product.ProductRequest;
import com.lelouch.cheeseandcream.model.product.ProductResponse;
import java.util.List;

public interface ProductCrudService {

    void createProduct(ProductRequest productData);
    ProductResponse getProductById(Long productId);
    List<ProductResponse> getProductsByAgentId(Long agentId);
    void updateProduct(Long productId, ProductRequest productData);
    void deleteProduct(Long productId);

}
