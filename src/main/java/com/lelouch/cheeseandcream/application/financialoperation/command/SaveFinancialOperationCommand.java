package com.lelouch.cheeseandcream.application.financialoperation.command;

import com.lelouch.cheeseandcream.domain.FinancialOperation;

public interface SaveFinancialOperationCommand {

    void save(FinancialOperation financialOperation);

}
