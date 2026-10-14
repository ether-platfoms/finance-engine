package et.etherplatforms.finance.spi;

import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.core.tax.TaxContext;

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
