package com.lelouch.cheeseandcream.mapper;

import com.lelouch.cheeseandcream.entity.agent.Agent;
import com.lelouch.cheeseandcream.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.model.agent.AgentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    @Mapping(target = "identificationType", source = "agent.identificationType.id")
    AgentResponse toResponse(Agent agent);
    Agent toEntity(AgentRequest agent);

}
