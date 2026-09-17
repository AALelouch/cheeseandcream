package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.domain.Agent;

public interface SaveAgentCommand {

    Agent save(Agent agent);
}
