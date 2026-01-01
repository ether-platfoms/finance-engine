package com.keystone.finance.core.tax;

import com.keystone.finance.core.money.Money;
import com.keystone.finance.core.money.CurrencyUnit;

import java.util.Objects;

public final class TaxResult {

    private final Money netAmount;
    private final Money taxAmount;
    private final Money totalAmount;
    private final String taxType;

    public TaxResult(Money netAmount, Money taxAmount, String taxType) {
        this.netAmount = Objects.requireNonNull(netAmount, "Net amount cannot be null");
        this.taxAmount = Objects.requireNonNull(taxAmount, "Tax amount cannot be null");
        this.totalAmount = netAmount.add(taxAmount);
        this.taxType = Objects.requireNonNull(taxType, "Tax type cannot be null");
    }

    public Money getNetAmount() {
        return netAmount;
    }

    public Money getTaxAmount() {
        return taxAmount;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public String getTaxType() {
        return taxType;
    }

    @Override
    public String toString() {
        return "TaxResult{" +
                "netAmount=" + netAmount +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", taxType='" + taxType + '\'' +
                '}';
    }
}
