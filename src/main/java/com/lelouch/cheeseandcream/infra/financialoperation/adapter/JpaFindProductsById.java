package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.FindProductsById;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.infra.orm.product.ProductEntity;
import com.lelouch.cheeseandcream.infra.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JpaFindProductsById implements FindProductsById {

    private final ProductRepository productRepository;

    public JpaFindProductsById(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllById(List<Long> productIds) {
        return productRepository.findAllByIdInAndActiveIsTrue(productIds).stream().map(ProductEntity::toDomain).toList();
    }
}
