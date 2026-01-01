package com.keystone.finance.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private final BigDecimal amount;
    private final CurrencyUnit currency;

    public Money(BigDecimal amount, CurrencyUnit currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and currency must not be null");
        }

        this.amount = amount.setScale(SCALE, ROUNDING);
        this.currency = currency;
    }

    /* =========================
       Accessors
       ========================= */

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    /* =========================
       Arithmetic
       ========================= */

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), currency);
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), currency);
    }

    public Money divide(int divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("Divisor must be positive");
        }
        return new Money(this.amount.divide(BigDecimal.valueOf(divisor), SCALE, ROUNDING), currency);
    }

    /* =========================
       Comparisons
       ========================= */

    public boolean isNegative() {
        return amount.signum() < 0;
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }

    public boolean isPositive() {
        return amount.signum() > 0;
    }

    /* =========================
       Internal guards
       ========================= */

    private void requireSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }
/* =========================
   Factory helpers
   ========================= */

    public static Money zero(CurrencyUnit currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    /* =========================
       Equality
       ========================= */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 &&
                currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCode();
    }
}
