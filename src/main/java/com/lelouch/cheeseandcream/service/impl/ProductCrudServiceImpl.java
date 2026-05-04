package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.entity.product.Category;
import com.lelouch.cheeseandcream.entity.product.Product;
import com.lelouch.cheeseandcream.exception.NotFoundException;
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

    public ProductCrudServiceImpl(ProductMapper productMapper, ProductRepository productRepository, CategoryRepository categoryRepository,
            CategoryRepository categoryRepository1) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository1;
    }

    @Override
    public void createProduct(ProductRequest productData) {
        Category category = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Product product = productMapper.toEntity(productData);
        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void updateProduct(Long productId, ProductRequest productData) {
        Category category = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Product product = productMapper.toEntity(productData);
        product.setId(productId);
        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
