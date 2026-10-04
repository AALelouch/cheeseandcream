package com.lelouch.cheeseandcream.infra.financialoperation;

import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FinancialOperationMapper {

    @Mapping(target = "idAgent", source = "agent.id")
    @Mapping(target = "date", source = "creationDate", qualifiedByName = "formatDate")
    FinancialOperationResponse toResponse(FinancialOperation financialOperation);

    @Named("formatDate")
    default String formatDate(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value);
    }
}
