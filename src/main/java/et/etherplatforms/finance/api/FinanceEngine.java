package et.etherplatforms.finance.api;

import et.etherplatforms.finance.core.accounting.Journal;
import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.core.period.AccountingPeriod;
import et.etherplatforms.finance.core.tax.TaxContext;

public interface FinanceEngine {

    Money calculateTax(Money netAmount, TaxContext context);

    void postJournal(Journal journal, AccountingPeriod period);

    String getVersion();
}
