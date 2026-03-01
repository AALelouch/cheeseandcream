package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.service.model.ProductRequest;
import com.lelouch.cheeseandcream.service.model.ProductResponse;
import java.util.List;

public interface ProductCrudService {

    void createProduct(ProductRequest productData);
    ProductResponse getProductById(Long productId);
    void updateProduct(Long productId, ProductRequest productData);
    List<ProductResponse> getAllProducts();
    void deleteProduct(Long productId);

}
