package com.lelouch.cheeseandcream.infra.orm.product;

import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.infra.orm.agent.AgentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(unique = true)
    private String name;
    private Double quantity = 0.0;
    private Double price = 0.0;
    private Double cost = 0.0;
    private String unitType;
    private boolean active = true;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "agent_id", nullable = false)
    private AgentEntity agentEntity;


    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    @Column(updatable = false)
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
 
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Product toDomain() {

        return Product.create(this.id, this.name, this.quantity, this.price, this.cost, this.unitType, Product.Category.create(this.categoryEntity.getId(), this.categoryEntity.getName()));
    }

    public static ProductEntity fromDomain(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setPrice(product.getPrice());
        productEntity.setCost(product.getCost());
        productEntity.setUnitType(product.getUnitType());
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(product.getCategory().getId());
        categoryEntity.setName(product.getCategory().getName());
        productEntity.setCategoryEntity(categoryEntity);
        return productEntity;
    }
}
