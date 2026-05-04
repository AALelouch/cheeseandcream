package com.lelouch.cheeseandcream.entity.product;

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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double quantity;
    private Double price;
    private Double cost;
    private String unitType;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

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
}
