package com.keystone.finance.api;

import com.keystone.finance.core.accounting.Journal;
import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.period.AccountingPeriod;
import com.keystone.finance.core.tax.TaxContext;

public interface FinanceEngine {

    Money calculateTax(Money netAmount, TaxContext context);

    void postJournal(Journal journal, AccountingPeriod period);

    String getVersion();
}
