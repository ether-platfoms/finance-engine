package et.etherplatforms.finance.core.depreciation;

import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.core.period.AccountingPeriod;
import et.etherplatforms.finance.util.ValidationUtils;

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
