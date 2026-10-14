package et.etherplatforms.finance.core.accounting.report;

import et.etherplatforms.finance.core.accounting.Account;
import et.etherplatforms.finance.core.accounting.TrialBalance;
import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.util.MoneyUtils;

import java.util.HashMap;
import java.util.Map;

public final class BalanceSheetGenerator {

    public static BalanceSheet generate(TrialBalance trialBalance) {

        Map<Account, Money> assets = new HashMap<>();
        Map<Account, Money> liabilities = new HashMap<>();
        Map<Account, Money> equity = new HashMap<>();

        for (Account account : trialBalance.getDebitTotals().keySet()) {
            Money debit = trialBalance.getDebitTotal(account);
            Money credit = trialBalance.getCreditTotal(account);

            Money balance = calculateNetBalance(debit, credit);

            switch (account.getType()) {
                case ASSET -> assets.put(account, balance);
                case LIABILITY -> liabilities.put(account, balance);
                case EQUITY -> equity.put(account, balance);
            }
        }

        return new BalanceSheet(assets, liabilities, equity);
    }

    private static Money calculateNetBalance(Money debit, Money credit) {
        if (debit == null) return credit.multiply(MoneyUtils.NEGATIVE_ONE);
        if (credit == null) return debit;
        return debit.subtract(credit);
    }
}
