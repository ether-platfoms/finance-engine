package com.keystone.finance.core.accounting.reversing;

import com.keystone.finance.core.accounting.Journal;
import com.keystone.finance.core.accounting.LedgerEntry;
import com.keystone.finance.core.audit.AuditMetadata;

import java.util.ArrayList;
import java.util.List;

public final class ReversingEntryGenerator {

    public static Journal reverse(Journal original, String newReference) {

        if (!original.isReversible()) {
            throw new IllegalArgumentException("Journal is not marked as reversible");
        }

        List<LedgerEntry> reversedEntries = new ArrayList<>();

        for (LedgerEntry entry : original.getEntries()) {
            reversedEntries.add(reverseEntry(entry));
        }

        return new Journal(
                newReference,
                reversedEntries,
                false,                          // reversible?
                original.getPeriodCode(),       // reuse the period code from the original journal
                new AuditMetadata("system", "Reversing journal entry") // audit info
        );

    }

    private static LedgerEntry reverseEntry(LedgerEntry entry) {
        return new LedgerEntry(
                entry.getAccount(),
                entry.getCredit(),
                entry.getDebit()
        );
    }
}
