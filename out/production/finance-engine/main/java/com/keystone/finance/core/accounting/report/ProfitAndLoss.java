package com.keystone.finance.core.accounting.report;

import com.keystone.finance.core.accounting.Account;
import com.keystone.finance.core.money.Money;

import java.util.Map;

public final class ProfitAndLoss {

    private final Map<Account, Money> revenues;
    private final Map<Account, Money> expenses;
    private final Money netProfit;

    public ProfitAndLoss(Map<Account, Money> revenues,
                         Map<Account, Money> expenses,
                         Money netProfit) {
        this.revenues = revenues;
        this.expenses = expenses;
        this.netProfit = netProfit;
    }

    public Map<Account, Money> getRevenues() {
        return revenues;
    }

    public Map<Account, Money> getExpenses() {
        return expenses;
    }

    public Money getNetProfit() {
        return netProfit;
    }
}
