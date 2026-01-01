package com.keystone.finance.core.guard;

import com.keystone.finance.core.accounting.Journal;
import com.keystone.finance.core.period.AccountingPeriod;

public final class PeriodIntegrityGuard {

    public static void ensureSamePeriod(Journal journal, AccountingPeriod period) {
        if (!journal.getPeriodCode().equals(period.getCode())) {
            throw new IllegalStateException("Journal period mismatch");
        }
    }
}
