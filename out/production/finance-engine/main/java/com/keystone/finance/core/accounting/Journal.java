package com.keystone.finance.core.accounting;

import com.keystone.finance.core.audit.AuditMetadata;
import com.keystone.finance.core.money.Money;
import com.keystone.finance.exception.UnbalancedJournalException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public final class Journal {

    private final String reference;
    private final LocalDateTime timestamp;
    private final List<LedgerEntry> entries;
    private final boolean reversible;
    private final String periodCode;
    private final AuditMetadata audit;

    public Journal(
            String reference,
            List<LedgerEntry> entries,
            boolean reversible,
            String periodCode,
            AuditMetadata audit
    ) {
        if (entries == null || entries.size() < 2) {
            throw new IllegalArgumentException("Journal must have at least two entries");
        }

        this.reference = Objects.requireNonNull(reference, "reference is required");
        this.entries = List.copyOf(entries); // defensive immutability
        this.reversible = reversible;
        this.periodCode = Objects.requireNonNull(periodCode, "periodCode is required");
        this.audit = Objects.requireNonNull(audit, "audit metadata is required");
        this.timestamp = LocalDateTime.now();

        validateBalanced();
    }

    /* =========================
       Accessors
       ========================= */

    public String getReference() {
        return reference;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<LedgerEntry> getEntries() {
        return entries;
    }

    public boolean isReversible() {
        return reversible;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public AuditMetadata getAudit() {
        return audit;
    }

    /* =========================
       Domain Validation
       ========================= */

    public void validateBalanced() {
        Money totalDebit = null;
        Money totalCredit = null;

        for (LedgerEntry entry : entries) {
            Objects.requireNonNull(entry, "Ledger entry cannot be null");

            if (entry.isDebit()) {
                totalDebit = (totalDebit == null)
                        ? entry.getDebit()
                        : totalDebit.add(entry.getDebit());
            }

            if (entry.isCredit()) {
                totalCredit = (totalCredit == null)
                        ? entry.getCredit()
                        : totalCredit.add(entry.getCredit());
            }
        }

        if (totalDebit == null || totalCredit == null) {
            throw new UnbalancedJournalException(
                    "Journal must contain both debit and credit entries"
            );
        }

        if (!totalDebit.equals(totalCredit)) {
            throw new UnbalancedJournalException(
                    "Unbalanced journal. Debit=" + totalDebit + ", Credit=" + totalCredit
            );
        }
    }
}
