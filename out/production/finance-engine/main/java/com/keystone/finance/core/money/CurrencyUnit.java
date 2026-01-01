package com.keystone.finance.core.money;

import java.util.Objects;

public final class CurrencyUnit {

    private final String code;

    public static final CurrencyUnit ETB = new CurrencyUnit("ETB");
    public static final CurrencyUnit USD = new CurrencyUnit("USD");

    public CurrencyUnit(String code) {
        if (code == null || code.length() != 3) {
            throw new IllegalArgumentException("Currency code must be ISO-4217");
        }
        this.code = code.toUpperCase();
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyUnit)) return false;
        CurrencyUnit that = (CurrencyUnit) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

