package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.application.financialoperation.command.SaveFinancialOperationCommand;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationDetailsResponse;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationRequest;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationResponse;
import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationTermRequest;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindDetailsQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindFinancialOperationByAgentIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindProductsByIdQuery;
import com.lelouch.cheeseandcream.application.financialoperation.query.SearchFinancialOperationsByTermQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.OperationType;
import com.lelouch.cheeseandcream.domain.Product;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinancialOperationInteractor implements FinancialOperationUseCase {

    private final SaveFinancialOperationCommand saveFinancialOperationCommand;
    private final FindFinancialOperationByAgentIdQuery findFinancialOperationByAgentIdQuery;
    private final SearchFinancialOperationsByTermQuery searchFinancialOperationsByTermQuery;
    private final FindAgentByIdQuery findAgentByIdQuery;
    private final FindProductsByIdQuery findProductsByIdQuery;
    private final FindDetailsQuery findDetailsQuery;
    private final FinancialOperationOutputPort financialOperationOutputPort;

    public FinancialOperationInteractor(SaveFinancialOperationCommand saveFinancialOperationCommand, FindAgentByIdQuery findAgentByIdQuery,
            FindProductsByIdQuery findProductsByIdQuery, FindFinancialOperationByAgentIdQuery findFinancialOperationByAgentIdQuery,
            SearchFinancialOperationsByTermQuery searchFinancialOperationsByTermQuery,
            FindDetailsQuery findDetailsQuery,
            FinancialOperationOutputPort financialOperationOutputPort) {
        this.saveFinancialOperationCommand = saveFinancialOperationCommand;
        this.findAgentByIdQuery = findAgentByIdQuery;
        this.findProductsByIdQuery = findProductsByIdQuery;
        this.findFinancialOperationByAgentIdQuery = findFinancialOperationByAgentIdQuery;
        this.searchFinancialOperationsByTermQuery = searchFinancialOperationsByTermQuery;
        this.findDetailsQuery = findDetailsQuery;
        this.financialOperationOutputPort = financialOperationOutputPort;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "agents",  allEntries = true),
            @CacheEvict(cacheNames = "agents-by-id", allEntries = true)
    })
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

            List<Long> productIds = financialOperationRequest.getProducts().keySet().stream().toList();
            List<Product> products = findProductsByIdQuery.findAllById(productIds);

            financialOperation.performProductBasedOperation(products, mapToOperationProducts(financialOperationRequest));

        }else{
            financialOperation.performSingleAmountOperation(financialOperationRequest.getAmount());
        }

        saveFinancialOperationCommand.save(financialOperation);

        if (financialOperationRequest.getAmount() != 0 && hasProducts){

            switch (financialOperationRequest.getOperationType()) {
                case SALE -> {

                    FinancialOperation supplyPayment = FinancialOperation.create(agent, new LinkedList<>(), financialOperationRequest.getConcept(), OperationType.CLIENT_PAYMENT);
                    supplyPayment.performSingleAmountOperation(financialOperationRequest.getAmount());
                    saveFinancialOperationCommand.save(supplyPayment);
                }
                case PURCHASE -> { 
                    FinancialOperation supplyPayment = FinancialOperation.create(agent, new LinkedList<>(), financialOperationRequest.getConcept(), OperationType.PAYMENT);
                    supplyPayment.performSingleAmountOperation(financialOperationRequest.getAmount());
                    saveFinancialOperationCommand.save(supplyPayment);
                }
                default -> throw new BadRequestException("Invalid operation type for adding payment helper: " + financialOperationRequest.getOperationType());
            }

        }

    }

    @Override
    @Transactional(readOnly = true)
    public Page<FinancialOperationResponse> getOperationsByAgentId(Long idAgent, Pageable pageable) {
        return financialOperationOutputPort.mapToResponse(findFinancialOperationByAgentIdQuery.findByAgentId(idAgent, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FinancialOperationResponse> searchOperations(Long agentId, FinancialOperationTermRequest term,
            Pageable pageable) {
        return financialOperationOutputPort.mapToResponse(
                searchFinancialOperationsByTermQuery.searchByTerm(agentId, term, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialOperationDetailsResponse getOperationDetails(Long id) {

        return new FinancialOperationDetailsResponse(findDetailsQuery.findDetailsById(id));
    }

    private HashMap<Long, FinancialOperation.OperationProduct> mapToOperationProducts(FinancialOperationRequest financialOperationRequest) {
        return financialOperationRequest.getProducts().entrySet().stream()
                .collect(HashMap::new, (map, entry) ->
                        map.put(entry.getKey(), FinancialOperation.OperationProduct.create(entry.getValue().getQuantity(), entry.getValue()
                                .getPrice())), HashMap::putAll);
    }
}
