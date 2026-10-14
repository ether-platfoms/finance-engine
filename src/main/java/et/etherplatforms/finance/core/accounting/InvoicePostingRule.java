package et.etherplatforms.finance.core.accounting;

import et.etherplatforms.finance.core.audit.AuditMetadata;
import et.etherplatforms.finance.core.money.Money;

import java.util.List;

public class InvoicePostingRule implements PostingRule<InvoicePostingRule.InvoiceData> {

    private final Account accountsReceivable;
    private final Account revenue;

    public InvoicePostingRule(Account accountsReceivable, Account revenue) {
        this.accountsReceivable = accountsReceivable;
        this.revenue = revenue;
    }

    @Override
    public Journal post(InvoiceData invoice) {

        LedgerEntry debitAR = new LedgerEntry(
                accountsReceivable,
                invoice.amount(),
                null
        );

        LedgerEntry creditRevenue = new LedgerEntry(
                revenue,
                null,
                invoice.amount()
        );

        return new Journal(
                invoice.reference(),
                List.of(debitAR, creditRevenue),
                false,
                "INV_POST",
                new AuditMetadata("system", "Invoice posting")
        );
    }


    /** Minimal business DTO */
    public record InvoiceData(String reference, Money amount) {}
}
