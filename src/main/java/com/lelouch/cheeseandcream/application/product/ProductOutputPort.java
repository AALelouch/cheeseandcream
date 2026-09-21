package com.lelouch.cheeseandcream.application.product;

import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import com.lelouch.cheeseandcream.domain.Product;
import org.springframework.data.domain.Page;

public interface ProductOutputPort {

    ProductResponse mapToResponse(Product product);
    Page<ProductResponse> mapToResponse(Page<Product> products);
}
