package com.keystone.finance.core.accounting;

import com.keystone.finance.core.money.Money;

import java.util.Objects;

public final class LedgerEntry {

    private final Account account;
    private final Money debit;
    private final Money credit;

    public LedgerEntry(Account account, Money debit, Money credit) {
        if (account == null) {
            throw new IllegalArgumentException("Account is required");
        }

        if ((debit == null && credit == null) ||
                (debit != null && credit != null)) {
            throw new IllegalArgumentException("Exactly one of debit or credit must be set");
        }

        this.account = account;
        this.debit = debit;
        this.credit = credit;
    }

    public Account getAccount() {
        return account;
    }

    public Money getDebit() {
        return debit;
    }

    public Money getCredit() {
        return credit;
    }

    public boolean isDebit() {
        return debit != null;
    }

    public boolean isCredit() {
        return credit != null;
    }
}
