package com.keystone.finance.core.depreciation;

import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.period.AccountingPeriod;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class DepreciationSchedule {

    private final Money assetCost;
    private final Money residualValue;
    private final int usefulLifeMonths;
    private final DepreciationMethod method;

    private final Map<Integer, Money> schedule = new LinkedHashMap<>();

    public DepreciationSchedule(
            Money assetCost,
            Money residualValue,
            int usefulLifeMonths,
            DepreciationMethod method) {

        this.assetCost = Objects.requireNonNull(assetCost);
        this.residualValue = Objects.requireNonNull(residualValue);
        this.usefulLifeMonths = usefulLifeMonths;
        this.method = Objects.requireNonNull(method);

        generateSchedule();
    }

    private void generateSchedule() {
        Money accumulated = Money.zero(assetCost.getCurrency());

        for (int i = 1; i <= usefulLifeMonths; i++) {
            Money periodDepreciation =
                    method.calculateForPeriod(
                            assetCost,
                            residualValue,
                            usefulLifeMonths,
                            null,
                            i
                    );

            accumulated = accumulated.add(periodDepreciation);

            // Final period correction (rounding safety)
            if (i == usefulLifeMonths) {
                Money expected = assetCost.subtract(residualValue);
                Money adjustment = expected.subtract(accumulated);
                periodDepreciation = periodDepreciation.add(adjustment);
            }

            schedule.put(i, periodDepreciation);
        }
    }

    public Money getDepreciationForPeriod(int periodIndex) {
        return schedule.get(periodIndex);
    }

    public Map<Integer, Money> getFullSchedule() {
        return Map.copyOf(schedule);
    }
}
