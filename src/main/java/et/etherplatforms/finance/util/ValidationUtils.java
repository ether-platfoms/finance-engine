package et.etherplatforms.finance.util;

import java.util.Objects;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void requirePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireNonNegative(int value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
    }


    public static <T> T requireNonNull(T value, String message) {
        return Objects.requireNonNull(value, message);
    }

    public static void requireNotBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
