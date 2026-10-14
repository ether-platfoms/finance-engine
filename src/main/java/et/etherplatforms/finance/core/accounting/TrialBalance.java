package et.etherplatforms.finance.core.accounting;

import et.etherplatforms.finance.core.money.Money;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TrialBalance {

    private final Map<Account, Money> debitTotals;
    private final Map<Account, Money> creditTotals;

    private TrialBalance(Map<Account, Money> debitTotals,
                         Map<Account, Money> creditTotals) {
        this.debitTotals = debitTotals;
        this.creditTotals = creditTotals;
    }

    public static TrialBalance fromJournals(List<Journal> journals) {

        Map<Account, Money> debitMap = new HashMap<>();
        Map<Account, Money> creditMap = new HashMap<>();

        for (Journal journal : journals) {
            for (LedgerEntry entry : journal.getEntries()) {

                if (entry.isDebit()) {
                    debitMap.merge(
                            entry.getAccount(),
                            entry.getDebit(),
                            Money::add
                    );
                }

                if (entry.isCredit()) {
                    creditMap.merge(
                            entry.getAccount(),
                            entry.getCredit(),
                            Money::add
                    );
                }
            }
        }

        return new TrialBalance(debitMap, creditMap);
    }

    public Map<Account, Money> getDebitTotals() {
        return Collections.unmodifiableMap(debitTotals);
    }

    public Map<Account, Money> getCreditTotals() {
        return Collections.unmodifiableMap(creditTotals);
    }

    public Money getDebitTotal(Account account) {
        return debitTotals.get(account);
    }

    public Money getCreditTotal(Account account) {
        return creditTotals.get(account);
    }
}
