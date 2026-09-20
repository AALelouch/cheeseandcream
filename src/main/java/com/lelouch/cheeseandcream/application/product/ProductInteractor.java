package com.lelouch.cheeseandcream.application.product;

import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductInteractor implements ProductUseCase {

    private final FindProductByIdQuery findProductByIdQuery;
    private final FindProductsByAgentIdQuery findProductsByAgentIdQuery;
    private final SearchProductsByTermQuery searchProductsByTermQuery;
    private final FindProductCategoryById findProductCategoryById;
    private final FindProductAgentById findProductAgentById;
    private final ExistsProductWithNameQuery existsProductWithNameQuery;
    private final SaveProductCommand saveProductCommand;
    private final DeactivateProductCommand deactivateProductCommand;
    private final ProductOutputPort productOutputPort;

    public ProductInteractor(FindProductByIdQuery findProductByIdQuery,
            FindProductsByAgentIdQuery findProductsByAgentIdQuery,
            SearchProductsByTermQuery searchProductsByTermQuery,
            FindProductCategoryById findProductCategoryById,
            FindProductAgentById findProductAgentById,
            ExistsProductWithNameQuery existsProductWithNameQuery,
            SaveProductCommand saveProductCommand,
            DeactivateProductCommand deactivateProductCommand,
            ProductOutputPort productOutputPort) {
        this.findProductByIdQuery = findProductByIdQuery;
        this.findProductsByAgentIdQuery = findProductsByAgentIdQuery;
        this.searchProductsByTermQuery = searchProductsByTermQuery;
        this.findProductCategoryById = findProductCategoryById;
        this.findProductAgentById = findProductAgentById;
        this.existsProductWithNameQuery = existsProductWithNameQuery;
        this.saveProductCommand = saveProductCommand;
        this.deactivateProductCommand = deactivateProductCommand;
        this.productOutputPort = productOutputPort;
    }

    @Override
    @Transactional
    public void createProduct(ProductRequest productRequest) {
        ValidatorUtils.validateData(() -> existsProductWithNameQuery.existsByNameAndAgentId(productRequest.name(), productRequest.agendId()),
                "ProductEntity with the same name already exists");
        saveProductCommand.save(toProduct(null, productRequest));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        return productOutputPort.mapToResponse(findActiveProduct(productId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByAgentId(Long agentId, Pageable pageable) {
        return productOutputPort.mapToResponse(findProductsByAgentIdQuery.findByAgentId(agentId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(Long agentId, ProductTermRequest term, Pageable pageable) {
        return productOutputPort.mapToResponse(searchProductsByTermQuery.searchByTerm(agentId, term, pageable));
    }

    @Override
    @Transactional
    public void updateProduct(Long productId, ProductRequest productRequest) {
        findActiveProduct(productId);
        ValidatorUtils.validateData(() -> existsProductWithNameQuery.existsByNameAndAgentIdExcludingId(
                productRequest.name(), productRequest.agendId(), productId), "ProductEntity with the same name already exists");
        saveProductCommand.save(toProduct(productId, productRequest));
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        findActiveProduct(productId);
        deactivateProductCommand.deactivate(productId);
    }

    private Product findActiveProduct(Long productId) {
        return findProductByIdQuery.findById(productId)
                .orElseThrow(() -> new NotFoundException("ProductEntity not found"));
    }

    private Product toProduct(Long productId, ProductRequest request) {
        Product.Category category = findProductCategoryById.findCategoryById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
        Product.Agent agent = findProductAgentById.findAgentById(request.agendId())
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        return Product.create(productId, request.name(), request.quantity(), request.price(), request.cost(),
                request.unitType(), category, agent);
    }
}
