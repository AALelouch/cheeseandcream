package com.lelouch.cheeseandcream.infra.mapper;

import com.lelouch.cheeseandcream.infra.orm.agent.AgentEntity;
import com.lelouch.cheeseandcream.application.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.application.model.agent.AgentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    @Mapping(target = "identificationType", source = "agentEntity.identificationTypeEntity.id")
    AgentResponse toResponse(AgentEntity agentEntity);
    AgentEntity toEntity(AgentRequest agent);

}
