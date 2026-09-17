package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.SaveFinancialOperationCommand;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentEntity;
import com.lelouch.cheeseandcream.infra.financialoperation.persistence.FinancialOperationEntity;
import com.lelouch.cheeseandcream.infra.financialoperation.persistence.OperationProductEntity;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductEntity;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentRepository;
import com.lelouch.cheeseandcream.infra.financialoperation.FinancialOperationRepository;
import com.lelouch.cheeseandcream.infra.product.persistence.ProductRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toMap;

@Service
public class SaveFinancialOperationAdapter implements SaveFinancialOperationCommand {

    private final FinancialOperationRepository financialOperationRepository;
    private final AgentRepository agentRepository;
    private final ProductRepository productRepository;

    public SaveFinancialOperationAdapter(FinancialOperationRepository financialOperationRepository,
            AgentRepository agentRepository, ProductRepository productRepository) {
        this.financialOperationRepository = financialOperationRepository;
        this.agentRepository = agentRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void save(FinancialOperation financialOperation) {
        AgentEntity managedAgent = agentRepository.findById(financialOperation.getAgent().getId())
                .orElseThrow(() -> new NotFoundException("Agent not found with id: " + financialOperation.getAgent().getId()));

        List<Long> productIds = financialOperation.getOperationProducts().stream()
                .map(operationProduct -> operationProduct.getProduct().getId())
                .toList();

        Map<Long, ProductEntity> managedProducts = productRepository.findAllByIdInAndActiveIsTrue(productIds).stream()
                .collect(toMap(ProductEntity::getId, Function.identity()));

        if (managedProducts.size() != productIds.size()) {
            throw new NotFoundException("One or more products not found with the provided ids.");
        }

        managedAgent.setBalance(financialOperation.getAgent().getBalance());
        
        FinancialOperationEntity entity = new FinancialOperationEntity();
        entity.setAgentEntity(managedAgent);
        entity.setConcept(financialOperation.getConcept());
        entity.setOperationType(financialOperation.getOperationType());
        entity.setTotal(financialOperation.getTotal());

        for (FinancialOperation.OperationProduct line : financialOperation.getOperationProducts()) {
            ProductEntity managedProduct =
                    managedProducts.get(line.getProduct().getId());

            managedProduct.setQuantity(line.getProduct().getQuantity());

            OperationProductEntity lineEntity = new OperationProductEntity();
            lineEntity.setProductEntity(managedProduct);
            lineEntity.setQuantity(line.getQuantity());
            lineEntity.setTotalPrice(line.getTotalPrice());

            entity.addProduct(lineEntity);
        }

        financialOperationRepository.save(entity);
    }
}
