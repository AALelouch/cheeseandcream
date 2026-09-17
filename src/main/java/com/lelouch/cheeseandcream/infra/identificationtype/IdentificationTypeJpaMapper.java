package com.lelouch.cheeseandcream.infra.identificationtype;

import com.lelouch.cheeseandcream.domain.IdentificationType;
import com.lelouch.cheeseandcream.infra.identificationtype.persistence.IdentificationTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdentificationTypeJpaMapper {

    @Mapping(target = "rename", ignore = true)
    IdentificationType toDomain(IdentificationTypeEntity identificationTypeEntity);
    IdentificationTypeEntity toEntity(IdentificationType identificationType);
}
