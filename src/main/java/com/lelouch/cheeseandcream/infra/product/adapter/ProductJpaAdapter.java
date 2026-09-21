package com.lelouch.cheeseandcream.infra.product.adapter;

import com.lelouch.cheeseandcream.application.product.command.DeactivateProductCommand;
import com.lelouch.cheeseandcream.application.product.query.ExistsProductWithNameQuery;
import com.lelouch.cheeseandcream.application.product.query.FindProductAgentById;
import com.lelouch.cheeseandcream.application.product.query.FindProductByIdQuery;
import com.lelouch.cheeseandcream.application.product.query.FindProductCategoryById;
import com.lelouch.cheeseandcream.application.product.query.FindProductsByAgentIdQuery;
import com.lelouch.cheeseandcream.application.product.command.SaveProductCommand;
import com.lelouch.cheeseandcream.application.product.dto.ProductTermRequest;
import com.lelouch.cheeseandcream.application.product.query.SearchProductsByTermQuery;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentEntity;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentRepository;
import com.lelouch.cheeseandcream.infra.category.persistence.CategoryEntity;
import com.lelouch.cheeseandcream.infra.category.persistence.CategoryRepository;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductEntity;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductJpaAdapter implements FindProductByIdQuery, FindProductsByAgentIdQuery,
        FindProductCategoryById, FindProductAgentById, ExistsProductWithNameQuery,
        SearchProductsByTermQuery, SaveProductCommand, DeactivateProductCommand {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AgentRepository agentRepository;

    public ProductJpaAdapter(ProductRepository productRepository, CategoryRepository categoryRepository,
            AgentRepository agentRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productRepository.findByIdAndActiveIsTrue(productId).map(ProductEntity::toDomain);
    }

    @Override
    public Page<Product> findByAgentId(Long agentId, Pageable pageable) {
        return productRepository.findByAgentEntityIdAndActiveIsTrue(agentId, pageable).map(ProductEntity::toDomain);
    }

    @Override
    public Page<Product> searchByTerm(Long agentId, ProductTermRequest term, Pageable pageable) {
        return productRepository.searchByAgentIdAndName(agentId, term.term(), pageable).map(ProductEntity::toDomain);
    }

    @Override
    public Optional<Product.Category> findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .filter(CategoryEntity::isActive)
                .map(category -> Product.Category.create(category.getId(), category.getName()));
    }

    @Override
    public Optional<Product.Agent> findAgentById(Long agentId) {
        return agentRepository.findById(agentId)
                .filter(AgentEntity::isActive)
                .map(agent -> Product.Agent.create(agent.getId(), agent.getName()));
    }

    @Override
    public boolean existsByNameAndAgentId(String name, Long agentId) {
        return productRepository.existsByNameAndActiveIsTrueAndAgentEntityId(name, agentId);
    }

    @Override
    public boolean existsByNameAndAgentIdExcludingId(String name, Long agentId, Long productId) {
        return productRepository.existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(name, productId, agentId);
    }

    @Override
    public void save(Product product) {
        ProductEntity entity = product.getId() == null ? new ProductEntity() : productRepository
                .findByIdAndActiveIsTrue(product.getId())
                .orElseThrow(() -> new NotFoundException("ProductEntity not found"));
        CategoryEntity category = categoryRepository.findById(product.getCategory().getId())
                .filter(CategoryEntity::isActive)
                .orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
        AgentEntity agent = agentRepository.findById(product.getAgent().getId())
                .filter(AgentEntity::isActive)
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));

        entity.setName(product.getName());
        entity.setQuantity(product.getQuantity());
        entity.setPrice(product.getPrice());
        entity.setCost(product.getCost());
        entity.setUnitType(product.getUnitType());
        entity.setCategoryEntity(category);
        entity.setAgentEntity(agent);
        productRepository.save(entity);
    }

    @Override
    public void deactivate(Long productId) {
        ProductEntity entity = productRepository.findByIdAndActiveIsTrue(productId)
                .orElseThrow(() -> new NotFoundException("ProductEntity not found"));
        entity.setActive(false);
        productRepository.save(entity);
    }
}
