package com.lelouch.cheeseandcream.application.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lelouch.cheeseandcream.application.category.command.SaveCategoryCommand;
import com.lelouch.cheeseandcream.application.category.dto.CategoryResponse;
import com.lelouch.cheeseandcream.application.category.query.CategoryNameExistsQuery;
import com.lelouch.cheeseandcream.application.category.query.FindAllCategoriesQuery;
import com.lelouch.cheeseandcream.application.category.query.FindCategoryByIdQuery;
import com.lelouch.cheeseandcream.domain.Category;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CategoryInteractorTest {

    @Test
    void createRejectsDuplicateNameWithoutPersisting() {
        Store store = new Store(null, true);
        CategoryInteractor interactor = new CategoryInteractor(store, store, store, store, new NoOpOutputPort());

        assertThrows(BadRequestException.class, () -> interactor.createCategory("Lacteos"));
        assertEquals(0, store.saveCalls);
    }

    @Test
    void updateRenamesAnExistingActiveCategory() {
        Store store = new Store(new Category(8L, "Quesos", true), false);
        CategoryInteractor interactor = new CategoryInteractor(store, store, store, store, new NoOpOutputPort());

        interactor.updateCategory(8L, "Quesos madurados");

        assertEquals(new Category(8L, "Quesos madurados", true), store.saved);
        assertEquals(8L, store.excludedId);
    }

    @Test
    void deleteRequiresAnExistingCategoryAndSoftDeletesIt() {
        Store store = new Store(new Category(8L, "Quesos", true), false);
        CategoryInteractor interactor = new CategoryInteractor(store, store, store, store, new NoOpOutputPort());

        interactor.deleteCategory(8L);

        assertFalse(store.saved.active());
        assertThrows(NotFoundException.class, () -> interactor.deleteCategory(99L));
    }

    private static final class Store implements SaveCategoryCommand, FindCategoryByIdQuery, FindAllCategoriesQuery,
            CategoryNameExistsQuery {
        private final Category existing;
        private final boolean duplicate;
        private Category saved;
        private Long excludedId;
        private int saveCalls;

        private Store(Category existing, boolean duplicate) {
            this.existing = existing;
            this.duplicate = duplicate;
        }

        @Override
        public void save(Category category) {
            saved = category;
            saveCalls++;
        }

        @Override
        public Optional<Category> findById(Long id) {
            return existing != null && existing.id().equals(id) ? Optional.of(existing) : Optional.empty();
        }

        @Override
        public java.util.List<Category> findAll() {
            return existing == null ? java.util.List.of() : java.util.List.of(existing);
        }

        @Override
        public boolean exists(String name, Long excludedId) {
            this.excludedId = excludedId;
            return duplicate;
        }
    }

    private static final class NoOpOutputPort implements CategoryOutputPort {
        @Override
        public CategoryResponse mapToResponse(Category category) {
            return null;
        }

        @Override
        public java.util.List<CategoryResponse> mapToResponse(java.util.List<Category> categories) {
            return java.util.List.of();
        }
    }
}
