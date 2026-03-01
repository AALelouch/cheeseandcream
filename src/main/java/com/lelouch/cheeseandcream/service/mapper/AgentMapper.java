package com.lelouch.cheeseandcream.service.mapper;

import com.lelouch.cheeseandcream.entity.Agent;
import com.lelouch.cheeseandcream.service.model.AgentRequest;
import com.lelouch.cheeseandcream.service.model.AgentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    AgentResponse entityToResponse(Agent agent);
    Agent requestToEntity(AgentRequest agent);

}
