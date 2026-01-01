package com.keystone.finance.core.accounting.report;

import com.keystone.finance.core.accounting.Account;
import com.keystone.finance.core.money.Money;

import java.util.Map;

public final class BalanceSheet {

    private final Map<Account, Money> assets;
    private final Map<Account, Money> liabilities;
    private final Map<Account, Money> equity;

    public BalanceSheet(Map<Account, Money> assets,
                        Map<Account, Money> liabilities,
                        Map<Account, Money> equity) {
        this.assets = assets;
        this.liabilities = liabilities;
        this.equity = equity;
    }

    public Map<Account, Money> getAssets() {
        return assets;
    }

    public Map<Account, Money> getLiabilities() {
        return liabilities;
    }

    public Map<Account, Money> getEquity() {
        return equity;
    }
}
