package com.lelouch.cheeseandcream.infra.agent;

import com.lelouch.cheeseandcream.application.agent.AgentUseCase;
import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentTermRequest;
import org.springframework.data.domain.Pageable;
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

    private final AgentUseCase agentUseCase;

    public AgentRestController(AgentUseCase agentUseCase) {
        this.agentUseCase = agentUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createAgent(@RequestBody AgentRequest agentRequest) {
        agentUseCase.createAgent(agentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAgent(@RequestBody AgentRequest agentRequest, @PathVariable Long id) {
        agentUseCase.updateAgent(id, agentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable Long id) {
        return new ResponseEntity<>(agentUseCase.getAgent(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<AgentResponse>> getAllAgents(Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.getAllAgents(pageable), HttpStatus.OK);
    }

    @GetMapping("/with-products")
    public ResponseEntity<Iterable<AgentResponse>> getAgentsWithProducts(Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.getAgentsWithProducts(pageable), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Iterable<AgentResponse>> searchAgents(@RequestBody AgentTermRequest term, Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.searchAgents(term, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentUseCase.deleteAgent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
