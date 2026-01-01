package com.keystone.finance.spi;

import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.tax.TaxContext;

public interface TaxPolicy {
    /**
     * Calculate tax for a given net amount and context.
     *
     * @param netAmount amount before tax
     * @param context tax context including rate, type, etc.
     * @return tax amount
     */
    Money calculate(Money netAmount, TaxContext context);
}
