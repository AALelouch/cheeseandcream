package com.lelouch.cheeseandcream.infra.adapter.in;

import com.lelouch.cheeseandcream.application.model.product.ProductResponse;
import com.lelouch.cheeseandcream.application.port.in.ProductRepositoryPort;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.mapper.ProductMapper;
import com.lelouch.cheeseandcream.infra.orm.product.ProductEntity;
import com.lelouch.cheeseandcream.infra.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductRepositoryAdapter(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductResponse> findByAgentEntityIdAndActiveIsTrue(Long agentId, Pageable pageable) {
        return productRepository.findByAgentEntityIdAndActiveIsTrue(agentId, pageable).map(productMapper::toResponse);
    }

    @Override
    public Product findByIdAndActiveIsTrue(Long id) {
        return productRepository.findByIdAndActiveIsTrue(id).orElseThrow(() -> new NotFoundException("Product not found")).toDomain();
    }

    @Override
    public List<Product> findAllByIdInAndActiveIsTrue(List<Long> productIds) {
        return productRepository.findAllByIdInAndActiveIsTrue(productIds).stream().map(ProductEntity::toDomain).toList();
    }

    @Override
    public boolean existsByNameAndActiveIsTrueAndAgentEntityId(String name, Long agentId) {
        return productRepository.existsByNameAndActiveIsTrueAndAgentEntityId(name, agentId);
    }

    @Override
    public boolean existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(String name, Long id, Long agentId) {
        return productRepository.existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(name, id, agentId);
    }
}
