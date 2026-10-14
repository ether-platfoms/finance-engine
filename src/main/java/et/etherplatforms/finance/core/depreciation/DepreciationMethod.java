package et.etherplatforms.finance.core.depreciation;

import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.core.period.AccountingPeriod;

public interface DepreciationMethod {

    Money calculateForPeriod(
            Money assetCost,
            Money residualValue,
            int usefulLifeMonths,
            AccountingPeriod period,
            int periodIndex
    );

    String getName();
}
