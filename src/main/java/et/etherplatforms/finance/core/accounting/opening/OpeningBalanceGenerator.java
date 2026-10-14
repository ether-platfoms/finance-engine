package et.etherplatforms.finance.core.accounting.opening;

import com.keystone.finance.core.accounting.*;
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

public final class OpeningBalanceGenerator {

    public static Journal generate(
            TrialBalance closingTrialBalance,
            String reference,
            String periodCode,
            String createdBy
    ) {

        List<LedgerEntry> entries = new ArrayList<>();

        for (Account account : closingTrialBalance.getDebitTotals().keySet()) {

            if (account.getType() == AccountType.REVENUE ||
                    account.getType() == AccountType.EXPENSE) {
                continue;
            }

            Money debit = closingTrialBalance.getDebitTotal(account);
            Money credit = closingTrialBalance.getCreditTotal(account);

            Money balance = calculateBalance(debit, credit);

            if (balance == null || balance.getAmount().signum() == 0) {
                continue;
            }

            entries.add(createOpeningEntry(account, balance));
        }

        if (entries.isEmpty()) {
            throw new IllegalStateException("No balances available for opening journal");
        }

        return new Journal(
                reference,
                entries,
                false,
                periodCode,
                new AuditMetadata(createdBy, "Opening balances")
        );

    }

    private static Money calculateBalance(Money debit, Money credit) {
        if (debit == null && credit == null) return null;
        if (debit == null) return credit.multiply(MoneyUtils.NEGATIVE_ONE);
        if (credit == null) return debit;
        return debit.subtract(credit);
    }

    private static LedgerEntry createOpeningEntry(Account account, Money balance) {
        if (balance.getAmount().signum() > 0) {
            return new LedgerEntry(account, balance, null);
        }
        return new LedgerEntry(
                account,
                null,
                balance.multiply(MoneyUtils.NEGATIVE_ONE)
        );
    }
}
