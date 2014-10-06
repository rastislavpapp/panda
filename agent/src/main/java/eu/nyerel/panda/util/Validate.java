package eu.nyerel.panda.util;

/**
 * @author Rastislav Papp (rastislav.papp@ibacz.eu))
 */
public class Validate {

    public static void notNull(Object o) {
        notNull(o, "The validated object is null");
    }

    public static void notEmpty(String string) {
        notEmpty(string, "The validated string is empty");
    }

    public static void notEmpty(String string, String message) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean condition) {
        isTrue(condition, "Validated condition is false");
    }

	public static void isFalse(boolean condition) {
		isTrue(!condition, "Validated condition is true");
	}

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
