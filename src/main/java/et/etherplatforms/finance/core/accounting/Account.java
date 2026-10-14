package et.etherplatforms.finance.core.accounting;

import java.util.Objects;

public final class Account {

    private final String code;
    private final String name;
    private final AccountType type;

    public Account(String code, String name, AccountType type) {
        if (code == null || name == null || type == null) {
            throw new IllegalArgumentException("Account fields must not be null");
        }
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return code.equals(account.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
