package com.keystone.finance.core.depreciation;

import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.period.AccountingPeriod;

public interface DepreciationMethod {

    /**
     * Calculates depreciation expense for a specific accounting period.
     */
    Money calculateForPeriod(
            Money assetCost,
            Money residualValue,
            int usefulLifeMonths,
            AccountingPeriod period,
            int periodIndex
    );

    String getName();
}
