package com.lelouch.cheeseandcream.infra.agent;

import com.lelouch.cheeseandcream.application.agent.AgentUseCase;
import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentTermRequest;
import com.lelouch.cheeseandcream.domain.Role;
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

    @GetMapping("/clients")
    public ResponseEntity<Iterable<AgentResponse>> getAllClient(Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.getAllAgents(pageable, Role.CLIENT), HttpStatus.OK);
    }

    @GetMapping("/providers")
    public ResponseEntity<Iterable<AgentResponse>> getAllProviders(Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.getAllAgents(pageable, Role.PROVIDER), HttpStatus.OK);
    }

    @PostMapping("/search/clients")
    public ResponseEntity<Iterable<AgentResponse>> searchClients(@RequestBody AgentTermRequest term, Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.searchAgents(term, Role.CLIENT, pageable), HttpStatus.OK);
    }

    @PostMapping("/search/providers")
    public ResponseEntity<Iterable<AgentResponse>> searchProviders(@RequestBody AgentTermRequest term, Pageable pageable) {
        return new ResponseEntity<>(agentUseCase.searchAgents(term, Role.PROVIDER, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentUseCase.deleteAgent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
