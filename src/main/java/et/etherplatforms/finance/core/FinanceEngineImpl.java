package et.etherplatforms.finance.core;

import et.etherplatforms.finance.api.FinanceEngine;
import et.etherplatforms.finance.core.accounting.Journal;
import et.etherplatforms.finance.core.guard.PeriodPostingGuard;
import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.core.period.AccountingPeriod;
import et.etherplatforms.finance.core.tax.TaxContext;
import et.etherplatforms.finance.core.tax.TaxResult;
import et.etherplatforms.finance.spi.TaxPolicy;

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
