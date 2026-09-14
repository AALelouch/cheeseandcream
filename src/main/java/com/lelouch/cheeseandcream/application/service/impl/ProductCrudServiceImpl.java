package com.lelouch.cheeseandcream.application.service.impl;

import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.infra.orm.agent.AgentEntity;
import com.lelouch.cheeseandcream.infra.orm.product.CategoryEntity;
import com.lelouch.cheeseandcream.infra.orm.product.ProductEntity;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.repository.AgentRepository;
import com.lelouch.cheeseandcream.infra.repository.CategoryRepository;
import com.lelouch.cheeseandcream.infra.repository.ProductRepository;
import com.lelouch.cheeseandcream.application.service.ProductCrudService;
import com.lelouch.cheeseandcream.infra.mapper.ProductMapper;
import com.lelouch.cheeseandcream.application.model.product.ProductRequest;
import com.lelouch.cheeseandcream.application.model.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCrudServiceImpl implements ProductCrudService {

    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AgentRepository agentRepository;

    public ProductCrudServiceImpl(ProductMapper productMapper, ProductRepository productRepository, CategoryRepository categoryRepository,
            CategoryRepository categoryRepository1, AgentRepository agentRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository1;
        this.agentRepository = agentRepository;
    }

    @Override
    public void createProduct(ProductRequest productData) {
        ValidatorUtils.validateData(()-> productRepository.existsByNameAndActiveIsTrueAndAgentEntityId(productData.name(), productData.agendId()), "ProductEntity with the same name already exists");

        CategoryEntity categoryEntity = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
        AgentEntity agentEntity = agentRepository.findById(productData.agendId())
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        ProductEntity productEntity = productMapper.toEntity(productData);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setAgentEntity(agentEntity);
        productRepository.save(productEntity);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return productRepository.findByIdAndActiveIsTrue(productId)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("ProductEntity not found"));
    }

    @Override
    public void updateProduct(Long productId, ProductRequest productData) {
        ValidatorUtils.validateData(()-> productRepository.existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(productData.name(), productId, productData.agendId()), "ProductEntity with the same name already exists");

        CategoryEntity categoryEntity = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
        AgentEntity agentEntity = agentRepository.findById(productData.agendId())
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        ProductEntity productEntity = productRepository.findByIdAndActiveIsTrue(productId)
                .orElseThrow(() -> new NotFoundException("ProductEntity not found"));
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setAgentEntity(agentEntity);
        productEntity.setQuantity(productData.quantity());
        productEntity.setName(productData.name());
        productEntity.setPrice(productData.price());
        productEntity.setCost(productData.cost());
        productEntity.setUnitType(productData.unitType());

        productRepository.save(productEntity);
    }

    @Override
    public Page<ProductResponse> getProductsByAgentId(Long agentId, Pageable pageable) {
        return productRepository.findByAgentEntityIdAndActiveIsTrue(agentId, pageable).map(productMapper::toResponse);
    }

    @Override
    public void deleteProduct(Long productId) {
        ProductEntity productEntity = productRepository.findByIdAndActiveIsTrue(productId).orElseThrow(() -> new NotFoundException("ProductEntity not found"));
        productEntity.setActive(false);
        productRepository.save(productEntity);
    }
}
