package et.etherplatforms.finance.core.tax;

import et.etherplatforms.finance.core.money.CurrencyUnit;
import java.math.BigDecimal;

public final class TaxContext {

    private final BigDecimal rate;
    private final CurrencyUnit currency;
    private final String taxType;

    public TaxContext(BigDecimal rate, CurrencyUnit currency, String taxType) {
        if (rate == null || currency == null || taxType == null) {
            throw new IllegalArgumentException("Rate, currency, and taxType cannot be null");
        }
        this.rate = rate;
        this.currency = currency;
        this.taxType = taxType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    public String getTaxType() {
        return taxType;
    }
}
