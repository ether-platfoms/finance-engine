package et.etherplatforms.finance.core.tax;

import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.spi.TaxPolicy;

import java.math.BigDecimal;

public final class DefaultTaxPolicy implements TaxPolicy {

    @Override
    public Money calculate(Money netAmount, TaxContext context) {
        if (netAmount == null || context == null) {
            throw new IllegalArgumentException("Net amount and context cannot be null");
        }

        if (!netAmount.getCurrency().equals(context.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch between net amount and tax context");
        }

        BigDecimal taxAmount = netAmount.getAmount().multiply(context.getRate());
        return new Money(taxAmount, netAmount.getCurrency());
    }
}
