package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.repository.InvoiceRepository;
import com.lelouch.cheeseandcream.service.DashBoardService;
import com.lelouch.cheeseandcream.model.dashboard.DashboardTimeRequest;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    private final AgentRepository agentRepository;
    private final InvoiceRepository invoiceRepository;

    public DashBoardServiceImpl(AgentRepository agentRepository, InvoiceRepository invoiceRepository) {
        this.agentRepository = agentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Double getTotalDebt() {
        Double totalDebt = 0.0;
        return agentRepository.findAll().stream().map(agent -> {
            if (agent.getBalance() != null && agent.getBalance() > 0) {
                return agent.getBalance();
            } else {
                return 0.0;
            }
        }).reduce(totalDebt, Double::sum);
    }

    @Override
    public Double getTotalDebtByTimeRange(DashboardTimeRequest dashboardTimeRequest) {
        Double totalDebt = 0.0;
        return agentRepository.findByModifiedDateBetween(dashboardTimeRequest.startTime(), dashboardTimeRequest.endTime()).stream().map(agent -> {
            if (agent.getBalance() != null && agent.getBalance() > 0) {
                return agent.getBalance();
            } else {
                return 0.0;
            }
        }).reduce(totalDebt, Double::sum);
    }

    @Override
    public Double getTotalRevenue() {
        Double totalRevenue = 0.0;
        return invoiceRepository.findAll().stream().map(invoice -> {
            if (invoice.getTotalAmount() != null && invoice.getTotalAmount() > 0) {
                return invoice.getTotalAmount();
            } else {
                return 0.0;
            }
        }).reduce(totalRevenue, Double::sum);
    }

    @Override
    public Double getTotalRevenueByTimeRange(DashboardTimeRequest dashboardTimeRequest) {
        Double totalRevenue = 0.0;
        return invoiceRepository.findByModifiedDateBetween(dashboardTimeRequest.startTime(), dashboardTimeRequest.endTime()).stream().map(invoice -> {
            if (invoice.getTotalAmount() != null && invoice.getTotalAmount() > 0) {
                return invoice.getTotalAmount();
            } else {
                return 0.0;
            }
        }).reduce(totalRevenue, Double::sum);
    }

    @Override
    public Double getTotalProfit() {
        Double totalProfit = 0.0;
        return invoiceRepository.findAll().stream().map(invoice -> {
            if (invoice.getTotalProfit() != null && invoice.getTotalProfit() > 0) {
                return invoice.getTotalProfit();
            } else {
                return 0.0;
            }
        }).reduce(totalProfit, Double::sum);
    }

    @Override
    public Double getTotalProfitByTimeRange(DashboardTimeRequest dashboardTimeRequest) {
        Double totalProfit = 0.0;
        return invoiceRepository.findByModifiedDateBetween(dashboardTimeRequest.startTime(), dashboardTimeRequest.endTime()).stream().map(invoice -> {
            if (invoice.getTotalProfit() != null && invoice.getTotalProfit() > 0) {
                return invoice.getTotalProfit();
            } else {
                return 0.0;
            }
        }).reduce(totalProfit, Double::sum);
    }
}
