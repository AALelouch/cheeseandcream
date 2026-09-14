package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.domain.FinancialOperation;

public interface SaveFinancialOperationCommand {

    void save(FinancialOperation financialOperation);

}
