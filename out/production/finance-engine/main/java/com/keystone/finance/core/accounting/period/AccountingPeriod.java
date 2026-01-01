package com.keystone.finance.core.accounting.period;

import java.time.LocalDate;
import java.util.Objects;

public final class AccountingPeriod {

    private final String code;          // e.g. 2025-01
    private final LocalDate startDate;
    private final LocalDate endDate;

    public AccountingPeriod(String code, LocalDate startDate, LocalDate endDate) {
        if (code == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Period fields must not be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean contains(LocalDate date) {
        return (date.equals(startDate) || date.isAfter(startDate)) &&
                (date.equals(endDate) || date.isBefore(endDate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountingPeriod)) return false;
        AccountingPeriod that = (AccountingPeriod) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
