package et.etherplatforms.finance.core.guard;

import et.etherplatforms.finance.core.period.AccountingPeriod;

public final class PeriodPostingGuard {

    public static void ensurePostingAllowed(AccountingPeriod period) {
        if (!period.isOpen()) {
            throw new IllegalStateException(
                    "Posting not allowed. Period " + period.getCode() + " is closed or locked"
            );
        }
    }
}
