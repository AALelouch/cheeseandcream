package com.lelouch.cheeseandcream.infra.product.adapter;

import com.lelouch.cheeseandcream.application.product.ProductResponse;
import com.lelouch.cheeseandcream.application.product.ProductOutputPort;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.infra.product.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductPresenter implements ProductOutputPort {

    private final ProductMapper productMapper;

    public ProductPresenter(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse mapToResponse(Product product) {
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> mapToResponse(Page<Product> products) {
        return products.map(productMapper::toResponse);
    }
}
