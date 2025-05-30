package common.util.watcher

import common.util.SharedConstants
import common.util.junit.TestDescription
import common.util.report.TestCategory
import common.util.report.TestCategory.Companion.getForCategory
import common.util.report.TestResult
import common.util.report.json.JsonReportGenerator
import org.junit.AssumptionViolatedException
import org.junit.experimental.categories.Category
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ReportTestWatcher : TestWatcher() {

    private val jsonReportGenerator = JsonReportGenerator()

    override fun succeeded(description: Description) {
        jsonReportGenerator.report(
            description.className,
            description.methodName,
            getTestDescription(description),
            SharedConstants.EMPTY,
            TestResult.SUCCESS,
            getTestCategory(description)
        )
    }

    override fun skipped(e: AssumptionViolatedException, description: Description) {
        jsonReportGenerator.report(
            description.className,
            description.methodName,
            getTestDescription(description),
            e.stackTraceToString(),
            TestResult.IGNORED,
            getTestCategory(description)
        )
    }

    override fun failed(e: Throwable, description: Description) {
        jsonReportGenerator.report(
            description.className,
            description.methodName,
            getTestDescription(description),
            e.stackTraceToString(),
            TestResult.FAILURE,
            getTestCategory(description)
        )
    }

    private fun getTestDescription(description: Description): String {
        val testDescription = description.getAnnotation(TestDescription::class.java)
        return testDescription?.value ?: SharedConstants.EMPTY
    }

    private fun getTestCategory(description: Description): TestCategory {
        val category = description.getAnnotation(Category::class.java)
        return if (category != null) {
            getForCategory(category)
        } else {
            TestCategory.AUTOMATED
        }
    }

}