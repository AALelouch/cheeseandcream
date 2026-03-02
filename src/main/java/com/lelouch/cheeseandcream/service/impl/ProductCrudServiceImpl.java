package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.entity.Product;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.repository.ProductRepository;
import com.lelouch.cheeseandcream.service.ProductCrudService;
import com.lelouch.cheeseandcream.service.mapper.ProductMapper;
import com.lelouch.cheeseandcream.service.model.product.ProductRequest;
import com.lelouch.cheeseandcream.service.model.product.ProductResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductCrudServiceImpl implements ProductCrudService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductCrudServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductRequest productData) {
        productRepository.save(productMapper.productRequestToProduct(productData));
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::productToResponse)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void updateProduct(Long productId, ProductRequest productData) {
        Product product = productMapper.productRequestToProduct(productData);
        product.setId(productId);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToResponse)
                .toList();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
