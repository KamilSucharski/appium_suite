package common.util.report

import org.junit.experimental.categories.Category

enum class TestCategory {
    AUTOMATED, SEMI_AUTOMATED, HELPER;

    companion object {
        @JvmStatic
        fun getForCategory(category: Category): TestCategory {
            return when (category.value[0].simpleName) {
                "Helper" -> HELPER
                "SemiAutomated" -> SEMI_AUTOMATED
                else -> AUTOMATED
            }
        }
    }
}