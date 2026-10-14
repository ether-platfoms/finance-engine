package et.etherplatforms.finance.core.accounting.fx;

import et.etherplatforms.finance.core.accounting.Account;
import et.etherplatforms.finance.core.accounting.Journal;
import et.etherplatforms.finance.core.accounting.LedgerEntry;
import et.etherplatforms.finance.core.audit.AuditMetadata;
import et.etherplatforms.finance.core.money.ExchangeRate;
import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.util.MoneyUtils;

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
            entries.add(new LedgerEntry(foreignAccount, difference, null));
            entries.add(new LedgerEntry(fxGainAccount, null, difference));
        } else {
            Money loss = difference.multiply(MoneyUtils.NEGATIVE_ONE);
            entries.add(new LedgerEntry(fxLossAccount, loss, null));
            entries.add(new LedgerEntry(foreignAccount, null, loss));
        }

        return new Journal(
                reference,
                entries,
                false,
                "FX_REVAL",
                new AuditMetadata("system", "Foreign currency revaluation")
        );

    }

}
