package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.AgentCrudService;
import com.lelouch.cheeseandcream.service.model.AgentRequest;
import com.lelouch.cheeseandcream.service.model.AgentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agents")
public class AgentRestController {

    private final AgentCrudService agentCrudService;

    public AgentRestController(AgentCrudService agentCrudService) {
        this.agentCrudService = agentCrudService;
    }

    @PostMapping
    public ResponseEntity<Void> createAgent(@RequestBody AgentRequest agentRequest) {
        agentCrudService.createAgent(agentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAgent(@RequestBody AgentRequest agentRequest, @PathVariable Long id) {
        agentCrudService.updateAgent(id, agentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable Long id) {
        return new ResponseEntity<>(agentCrudService.getAgent(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<AgentResponse>> getAllAgents() {
        return new ResponseEntity<>(agentCrudService.getAllAgents(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentCrudService.deleteAgent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
