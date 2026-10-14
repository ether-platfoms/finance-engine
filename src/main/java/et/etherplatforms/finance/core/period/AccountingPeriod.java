package et.etherplatforms.finance.core.period;

import java.time.LocalDate;
import java.util.Objects;

public final class AccountingPeriod {

    private final String code;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private PeriodStatus status;

    public AccountingPeriod(String code, LocalDate startDate, LocalDate endDate) {
        this.code = Objects.requireNonNull(code);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
        this.status = PeriodStatus.OPEN;
    }

    public void close() {
        ensureOpen();
        this.status = PeriodStatus.CLOSED;
    }

    public void lock() {
        if (status != PeriodStatus.CLOSED) {
            throw new IllegalStateException("Only closed periods can be locked");
        }
        this.status = PeriodStatus.LOCKED;
    }

    public boolean isOpen() {
        return status == PeriodStatus.OPEN;
    }

    public boolean isLocked() {
        return status == PeriodStatus.LOCKED;
    }

    private void ensureOpen() {
        if (!isOpen()) {
            throw new IllegalStateException("Period is not open");
        }
    }

    public String getCode() {
        return code;
    }
}
