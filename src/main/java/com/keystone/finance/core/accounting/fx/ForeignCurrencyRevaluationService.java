package com.keystone.finance.core.accounting.fx;

import com.keystone.finance.core.accounting.*;
import com.keystone.finance.core.audit.AuditMetadata;
import com.keystone.finance.core.money.*;
import com.keystone.finance.util.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

public final class ForeignCurrencyRevaluationService {

    public static Journal revalue(
            Account foreignAccount,
            Money foreignBalance,
            ExchangeRate closingRate,
            Money bookValue,
            Account fxGainAccount,
            Account fxLossAccount,
            String reference) {

        Money revaluedAmount = closingRate.convert(foreignBalance);
        Money difference = revaluedAmount.subtract(bookValue);

        if (difference.isZero()) {
            throw new IllegalStateException("No revaluation required");
        }

        List<LedgerEntry> entries = new ArrayList<>();

        if (difference.isPositive()) {
            // Gain
            entries.add(new LedgerEntry(foreignAccount, difference, null));
            entries.add(new LedgerEntry(fxGainAccount, null, difference));
        } else {
            // Loss
            Money loss = difference.multiply(MoneyUtils.NEGATIVE_ONE);
            entries.add(new LedgerEntry(fxLossAccount, loss, null));
            entries.add(new LedgerEntry(foreignAccount, null, loss));
        }

        return new Journal(
                reference,
                entries,
                false,
                "FX_REVAL",
                new AuditMetadata("system", "Foreign currency revaluation")// only 1 argument
        );

    }

}
