package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FinancialOperationInteractor implements FinancialOperationUseCase {

    private final SaveFinancialOperationCommand saveFinancialOperationCommand;
    private final FindFinancialOperationByAgentIdQuery findFinancialOperationByAgentIdQuery;
    private final FindAgentById findAgentById;
    private final FindProductsById findProductsById;
    private final FinancialOperationOutputPort financialOperationOutputPort;

    public FinancialOperationInteractor(SaveFinancialOperationCommand saveFinancialOperationCommand, FindAgentById findAgentById,
            FindProductsById findProductsById, FindFinancialOperationByAgentIdQuery findFinancialOperationByAgentIdQuery, FinancialOperationOutputPort financialOperationOutputPort) {
        this.saveFinancialOperationCommand = saveFinancialOperationCommand;
        this.findAgentById = findAgentById;
        this.findProductsById = findProductsById;
        this.findFinancialOperationByAgentIdQuery = findFinancialOperationByAgentIdQuery;
        this.financialOperationOutputPort = financialOperationOutputPort;
    }

    @Override
    @Transactional
    public void addOperation(FinancialOperationRequest financialOperationRequest) {



        Agent agent = findAgentById.findById(financialOperationRequest.getIdAgent())
                .orElseThrow(() -> new NotFoundException("AgentEntity not found with id: " + financialOperationRequest.getIdAgent()));

        FinancialOperation financialOperation = FinancialOperation
                .create(agent, new LinkedList<>(), financialOperationRequest.getConcept(), financialOperationRequest.getOperationType());

        if (financialOperationRequest.getProducts() == null) {
            throw new BadRequestException("Products list must not be null; send an empty list when there are no products");
        }

        boolean hasProducts = !financialOperationRequest.getProducts().isEmpty();

        if (hasProducts) {

            if (financialOperationRequest.getAmount() != 0) {
                throw new BadRequestException("Amount must be 0 when products are provided");
            }

            List<Long> productIds = financialOperationRequest.getProducts().keySet().stream().toList();
            List<Product> products = findProductsById.findAllById(productIds);

            financialOperation.performProductBasedOperation(products, financialOperationRequest.getProducts());
        }else{
            financialOperation.performSingleAmountOperation(financialOperationRequest.getAmount());
        }

        saveFinancialOperationCommand.save(financialOperation);
    }

    @Override
    public Page<FinancialOperationResponse> getOperationsByAgentId(Long idAgent, Pageable pageable) {
        return financialOperationOutputPort.mapToResponse(findFinancialOperationByAgentIdQuery.findByAgentId(idAgent, pageable));
    }
}
