package et.etherplatforms.finance.core.guard;

import et.etherplatforms.finance.core.accounting.Journal;
import et.etherplatforms.finance.core.period.AccountingPeriod;

public final class PeriodIntegrityGuard {

    public static void ensureSamePeriod(Journal journal, AccountingPeriod period) {
        if (!journal.getPeriodCode().equals(period.getCode())) {
            throw new IllegalStateException("Journal period mismatch");
        }
    }
}
