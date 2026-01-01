package com.keystone.finance.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class ExchangeRate {

    private final CurrencyUnit from;
    private final CurrencyUnit to;
    private final BigDecimal rate;

    public ExchangeRate(CurrencyUnit from, CurrencyUnit to, BigDecimal rate) {
        if (from == null || to == null || rate == null) {
            throw new IllegalArgumentException("Exchange rate fields must not be null");
        }
        if (rate.signum() <= 0) {
            throw new IllegalArgumentException("Exchange rate must be positive");
        }
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public CurrencyUnit getFrom() {
        return from;
    }

    public CurrencyUnit getTo() {
        return to;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Money convert(Money source) {
        if (!source.getCurrency().equals(from)) {
            throw new IllegalArgumentException("Source currency mismatch");
        }

        return new Money(
                source.getAmount().multiply(rate).setScale(2, RoundingMode.HALF_UP),
                to
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate)) return false;
        ExchangeRate that = (ExchangeRate) o;
        return from.equals(that.from) &&
                to.equals(that.to) &&
                rate.compareTo(that.rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, rate);
    }
}
