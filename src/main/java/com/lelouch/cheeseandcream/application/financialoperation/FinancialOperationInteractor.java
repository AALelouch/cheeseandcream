package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.application.financialoperation.command.SaveFinancialOperationCommand;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationResponse;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationTermRequest;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindFinancialOperationByAgentIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindProductsByIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.SearchFinancialOperationsByTermQuery;
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
    private final SearchFinancialOperationsByTermQuery searchFinancialOperationsByTermQuery;
    private final FindAgentByIdQuery findAgentByIdQuery;
    private final FindProductsByIdQuery findProductsByIdQuery;
    private final FinancialOperationOutputPort financialOperationOutputPort;

    public FinancialOperationInteractor(SaveFinancialOperationCommand saveFinancialOperationCommand, FindAgentByIdQuery findAgentByIdQuery,
            FindProductsByIdQuery findProductsByIdQuery, FindFinancialOperationByAgentIdQuery findFinancialOperationByAgentIdQuery,
            SearchFinancialOperationsByTermQuery searchFinancialOperationsByTermQuery,
            FinancialOperationOutputPort financialOperationOutputPort) {
        this.saveFinancialOperationCommand = saveFinancialOperationCommand;
        this.findAgentByIdQuery = findAgentByIdQuery;
        this.findProductsByIdQuery = findProductsByIdQuery;
        this.findFinancialOperationByAgentIdQuery = findFinancialOperationByAgentIdQuery;
        this.searchFinancialOperationsByTermQuery = searchFinancialOperationsByTermQuery;
        this.financialOperationOutputPort = financialOperationOutputPort;
    }

    @Override
    @Transactional
    public void addOperation(FinancialOperationRequest financialOperationRequest) {



        Agent agent = findAgentByIdQuery.findById(financialOperationRequest.getIdAgent())
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
            List<Product> products = findProductsByIdQuery.findAllById(productIds);

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

    @Override
    public Page<FinancialOperationResponse> searchOperations(Long agentId, FinancialOperationTermRequest term,
            Pageable pageable) {
        return financialOperationOutputPort.mapToResponse(
                searchFinancialOperationsByTermQuery.searchByTerm(agentId, term, pageable));
    }
}
