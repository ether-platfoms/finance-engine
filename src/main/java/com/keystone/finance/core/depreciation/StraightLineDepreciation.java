package com.keystone.finance.core.depreciation;

import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.period.AccountingPeriod;
import com.keystone.finance.util.ValidationUtils;

public final class StraightLineDepreciation implements DepreciationMethod {

    @Override
    public Money calculateForPeriod(
            Money assetCost,
            Money residualValue,
            int usefulLifeMonths,
            AccountingPeriod period,
            int periodIndex) {

        ValidationUtils.requirePositive(usefulLifeMonths, "Useful life must be positive");

        Money depreciableBase = assetCost.subtract(residualValue);

        if (depreciableBase.isNegative()) {
            throw new IllegalArgumentException("Residual value cannot exceed asset cost");
        }

        return depreciableBase.divide(usefulLifeMonths);
    }

    @Override
    public String getName() {
        return "STRAIGHT_LINE";
    }
}
