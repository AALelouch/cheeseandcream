package com.lelouch.cheeseandcream.mapper;

import com.lelouch.cheeseandcream.entity.financial.operation.FinancialOperation;
import com.lelouch.cheeseandcream.entity.financial.operation.OperationProduct;
import com.lelouch.cheeseandcream.model.operation.FinancialOperationResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FinancialOperationMapper {

    @Mapping(target = "idAgent", source = "agent.id")
    @Mapping(target = "productResponses", source = "products")
    @Mapping(target = "date", source = "creationDate", qualifiedByName = "formatDate")
    FinancialOperationResponse toResponse(FinancialOperation financialOperation);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "totalPrice", source = "totalPrice")
    FinancialOperationResponse.ProductResponse toProductResponse(OperationProduct operationProduct);

    @Named("formatDate")
    default String formatDate(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value);
    }
}
