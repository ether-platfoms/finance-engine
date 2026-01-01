package com.keystone.finance.core;

import com.keystone.finance.api.FinanceEngine;
import com.keystone.finance.core.accounting.Journal;
import com.keystone.finance.core.guard.PeriodPostingGuard;
import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.period.AccountingPeriod;
import com.keystone.finance.core.tax.TaxContext;
import com.keystone.finance.core.tax.TaxResult;
import com.keystone.finance.spi.TaxPolicy;

public class FinanceEngineImpl implements FinanceEngine {

    private final TaxPolicy taxPolicy;

    public FinanceEngineImpl(TaxPolicy taxPolicy) {
        this.taxPolicy = taxPolicy;
    }

    @Override
    public Money calculateTax(Money netAmount, TaxContext context) {
        return taxPolicy.calculate(netAmount, context);
    }

    public TaxResult calculateTaxResult(Money netAmount, TaxContext context) {
        Money tax = taxPolicy.calculate(netAmount, context);
        return new TaxResult(netAmount, tax, context.getTaxType());
    }

    @Override
    public void postJournal(Journal journal, AccountingPeriod period) {
        PeriodPostingGuard.ensurePostingAllowed(period);
        journal.validateBalanced();
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
