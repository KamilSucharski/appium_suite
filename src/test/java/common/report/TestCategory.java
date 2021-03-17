package common.report;

import org.junit.experimental.categories.Category;

public enum TestCategory {
    AUTOMATED, SEMI_AUTOMATED, HELPER;

    public static TestCategory getForCategory(final Category category) {
        switch (category.value()[0].getSimpleName()) {
            case "Helper": return TestCategory.HELPER;
            case "SemiAutomated": return TestCategory.SEMI_AUTOMATED;
            default: return TestCategory.AUTOMATED;
        }
    }
}