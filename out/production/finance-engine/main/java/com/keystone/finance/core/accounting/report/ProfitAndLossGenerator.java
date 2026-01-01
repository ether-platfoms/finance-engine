package com.keystone.finance.core.accounting.report;

import com.keystone.finance.core.accounting.Account;
import com.keystone.finance.core.accounting.AccountType;
import com.keystone.finance.core.accounting.TrialBalance;
import com.keystone.finance.core.money.Money;
import com.keystone.finance.util.MoneyUtils;

import java.util.HashMap;
import java.util.Map;

public final class ProfitAndLossGenerator {

    public static ProfitAndLoss generate(TrialBalance trialBalance) {

        Map<Account, Money> revenues = new HashMap<>();
        Map<Account, Money> expenses = new HashMap<>();

        Money totalRevenue = null;
        Money totalExpense = null;

        for (Account account : trialBalance.getCreditTotals().keySet()) {

            Money debit = trialBalance.getDebitTotal(account);
            Money credit = trialBalance.getCreditTotal(account);

            Money balance = calculateNetBalance(debit, credit);

            if (account.getType() == AccountType.REVENUE) {
                revenues.put(account, balance);
                totalRevenue = (totalRevenue == null) ? balance : totalRevenue.add(balance);
            }

            if (account.getType() == AccountType.EXPENSE) {
                expenses.put(account, balance);
                totalExpense = (totalExpense == null) ? balance : totalExpense.add(balance);
            }
        }

        Money netProfit = totalRevenue.subtract(totalExpense);

        return new ProfitAndLoss(revenues, expenses, netProfit);
    }

    private static Money calculateNetBalance(Money debit, Money credit) {
        if (credit == null) return debit;
        if (debit == null) return credit.multiply(MoneyUtils.NEGATIVE_ONE);
        return credit.subtract(debit);
    }
}
