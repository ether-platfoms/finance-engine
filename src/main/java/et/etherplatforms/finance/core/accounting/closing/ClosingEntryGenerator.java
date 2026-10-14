package et.etherplatforms.finance.core.accounting.closing;

import et.etherplatforms.finance.core.accounting.Account;
import et.etherplatforms.finance.core.accounting.AccountType;
import et.etherplatforms.finance.core.accounting.Journal;
import et.etherplatforms.finance.core.accounting.LedgerEntry;
import et.etherplatforms.finance.core.accounting.TrialBalance;
import et.etherplatforms.finance.core.audit.AuditMetadata;
import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.util.MoneyUtils;

import java.util.ArrayList;
import java.util.List;

public final class ClosingEntryGenerator {

    public static Journal generate(
            TrialBalance trialBalance,
            Account retainedEarnings,
            String reference,
            String periodCode,
            String createdBy
    ) {

        List<LedgerEntry> entries = new ArrayList<>();
        Money netResult = null;

        for (Account account : trialBalance.getCreditTotals().keySet()) {

            if (account.getType() != AccountType.REVENUE &&
                    account.getType() != AccountType.EXPENSE) {
                continue;
            }

            Money debit = trialBalance.getDebitTotal(account);
            Money credit = trialBalance.getCreditTotal(account);

            Money balance = calculateBalance(account, debit, credit);

            if (balance == null || balance.getAmount().signum() == 0) {
                continue;
            }

            entries.add(createClosingLine(account, balance));

            netResult = (netResult == null)
                    ? balance
                    : netResult.add(balance);
        }

        if (netResult == null) {
            throw new IllegalStateException("No P&L balances to close");
        }

        entries.add(createRetainedEarningsLine(retainedEarnings, netResult));

        return new Journal(
                reference,
                entries,
                false,
                periodCode,
                new AuditMetadata(createdBy, "Closing P&L entries")
        );

    }

    private static Money calculateBalance(Account account, Money debit, Money credit) {
        if (account.getType() == AccountType.REVENUE) {
            return credit == null ? null : credit.subtract(debit);
        }
        return debit == null ? null : debit.subtract(credit);
    }

    private static LedgerEntry createClosingLine(Account account, Money balance) {
        if (balance.getAmount().signum() > 0) {
            return new LedgerEntry(account, balance, null);
        }
        return new LedgerEntry(
                account,
                null,
                balance.multiply(MoneyUtils.NEGATIVE_ONE)
        );
    }

    private static LedgerEntry createRetainedEarningsLine(
            Account retainedEarnings,
            Money netResult) {

        if (netResult.getAmount().signum() > 0) {
            return new LedgerEntry(retainedEarnings, null, netResult);
        }
        return new LedgerEntry(
                retainedEarnings,
                netResult.multiply(MoneyUtils.NEGATIVE_ONE),
                null
        );
    }
}
