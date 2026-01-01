package com.keystone.finance.core.accounting;

public interface PostingRule<T> {

    /**
     * Converts a business object into an accounting journal.
     */
    Journal post(T source);
}
