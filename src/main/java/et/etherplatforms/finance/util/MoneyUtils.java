package et.etherplatforms.finance.util;

import et.etherplatforms.finance.core.money.CurrencyUnit;
import et.etherplatforms.finance.core.money.Money;

import java.math.BigDecimal;

public final class MoneyUtils {

    private MoneyUtils() {}

    public static final BigDecimal ONE = BigDecimal.ONE;
    public static final BigDecimal NEGATIVE_ONE = BigDecimal.valueOf(-1);

    public static Money zero(CurrencyUnit currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public static Money negate(Money money) {
        return new Money(
                money.getAmount().multiply(NEGATIVE_ONE),
                money.getCurrency()
        );
    }

    public static Money max(Money a, Money b) {
        requireSameCurrency(a, b);
        return a.getAmount().compareTo(b.getAmount()) >= 0 ? a : b;
    }

    public static Money min(Money a, Money b) {
        requireSameCurrency(a, b);
        return a.getAmount().compareTo(b.getAmount()) <= 0 ? a : b;
    }

    private static void requireSameCurrency(Money a, Money b) {
        if (!a.getCurrency().equals(b.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }
}
