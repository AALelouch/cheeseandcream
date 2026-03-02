package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.service.model.product.ProductRequest;
import com.lelouch.cheeseandcream.service.model.product.ProductResponse;
import java.util.List;

public interface ProductCrudService {

    void createProduct(ProductRequest productData);
    ProductResponse getProductById(Long productId);
    void updateProduct(Long productId, ProductRequest productData);
    List<ProductResponse> getAllProducts();
    void deleteProduct(Long productId);

}
