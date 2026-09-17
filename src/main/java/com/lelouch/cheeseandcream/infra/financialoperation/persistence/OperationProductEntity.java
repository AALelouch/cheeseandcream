package com.lelouch.cheeseandcream.infra.financialoperation.persistence;

import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "operation_product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantity;
    private Double totalPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id", nullable = false)
    private FinancialOperationEntity financialOperationEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    public FinancialOperation.OperationProduct toDomain() {
        return FinancialOperation.OperationProduct.create(
                this.quantity,
                this.totalPrice,
                this.productEntity.toDomain()
        );
    }

}

