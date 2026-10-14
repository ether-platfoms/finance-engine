package et.etherplatforms.finance.core.accounting;

public interface PostingRule<T> {

    Journal post(T source);
}
