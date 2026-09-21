package com.lelouch.cheeseandcream.application.agent.command;

import com.lelouch.cheeseandcream.domain.Agent;

public interface SaveAgentCommand {

    Agent save(Agent agent);
}
