package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.query.FindProductsByIdQuery;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductEntity;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JpaFindProductsByIdQuery implements FindProductsByIdQuery {

    private final ProductRepository productRepository;

    public JpaFindProductsByIdQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllById(List<Long> productIds) {
        return productRepository.findAllByIdInAndActiveIsTrue(productIds).stream().map(ProductEntity::toDomain).toList();
    }
}
