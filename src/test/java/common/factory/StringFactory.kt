package common.factory;

public final class StringFactory {

    private static final char[] ALLOWED_CHARACTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String createString(final int length) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(ALLOWED_CHARACTERS[i % ALLOWED_CHARACTERS.length]);
        }
        return builder.toString();
    }
}