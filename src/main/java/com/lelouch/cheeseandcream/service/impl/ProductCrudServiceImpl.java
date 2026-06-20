package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.ValidatorUtils;
import com.lelouch.cheeseandcream.entity.agent.Agent;
import com.lelouch.cheeseandcream.entity.product.Category;
import com.lelouch.cheeseandcream.entity.product.Product;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.repository.CategoryRepository;
import com.lelouch.cheeseandcream.repository.ProductRepository;
import com.lelouch.cheeseandcream.service.ProductCrudService;
import com.lelouch.cheeseandcream.mapper.ProductMapper;
import com.lelouch.cheeseandcream.model.product.ProductRequest;
import com.lelouch.cheeseandcream.model.product.ProductResponse;
import java.util.List;
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
        ValidatorUtils.validateData(()-> productRepository.existsByNameAndActiveIsTrueAndAgentId(productData.name(), productData.agendId()), "Product with the same name already exists");

        Category category = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Agent agent = agentRepository.findById(productData.agendId())
                .orElseThrow(() -> new NotFoundException("Agent not found"));
        Product product = productMapper.toEntity(productData);
        product.setCategory(category);
        product.setAgent(agent);
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return productRepository.findByIdAndActiveIsTrue(productId)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void updateProduct(Long productId, ProductRequest productData) {
        ValidatorUtils.validateData(()-> productRepository.existsByNameAndActiveIsTrueAndIdNotAndAgentId(productData.name(), productId, productData.agendId()), "Product with the same name already exists");

        Category category = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Agent agent = agentRepository.findById(productData.agendId())
                .orElseThrow(() -> new NotFoundException("Agent not found"));
        Product product = productMapper.toEntity(productData);
        product.setId(productId);
        product.setCategory(category);
        product.setAgent(agent);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getProductsByAgentId(Long agentId) {
        return productRepository.findByAgentIdAndActiveIsTrue(agentId).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findByIdAndActiveIsTrue(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }
}
