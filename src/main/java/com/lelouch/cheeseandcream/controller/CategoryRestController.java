package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.model.product.CategoryResponse;
import com.lelouch.cheeseandcream.service.CategoryCrudService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryCrudService categoryCrudService;

    public CategoryRestController(CategoryCrudService categoryCrudService) {
        this.categoryCrudService = categoryCrudService;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getCategory() {
        return ResponseEntity.ok(categoryCrudService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryCrudService.getCategoryById(id));
    }

    @PostMapping()
    public ResponseEntity<Void> post(@RequestParam String categoryName) {
        categoryCrudService.createCategory(categoryName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> put(@PathVariable Long id, @RequestParam String categoryName) {
        categoryCrudService.updateCategory(id, categoryName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryCrudService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
