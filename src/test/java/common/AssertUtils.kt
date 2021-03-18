package common;

import static org.junit.Assume.assumeTrue;

public final class AssertUtils {

    @SuppressWarnings("ConstantConditions")
    public static void ignoreTest(final String reason) {
        assumeTrue(reason, false);
    }
}