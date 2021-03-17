package common.watcher;

import common.junit.TestDescription;
import common.report.TestCategory;
import common.report.TestResult;
import common.report.json.JsonReportGenerator;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.AssumptionViolatedException;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ReportTestWatcher extends TestWatcher {

    private final JsonReportGenerator jsonReportGenerator = new JsonReportGenerator();
    private final String EMPTY = "";

    @Override
    protected void succeeded(final Description description) {
        jsonReportGenerator.report(
            description.getClassName(),
            description.getMethodName(),
            getTestDescription(description),
            EMPTY,
            TestResult.SUCCESS,
            getTestCategory(description)
        );
    }

    @Override
    protected void skipped(final AssumptionViolatedException e,
                           final Description description) {
        jsonReportGenerator.report(
            description.getClassName(),
            description.getMethodName(),
            getTestDescription(description),
            ExceptionUtils.getStackTrace(e),
            TestResult.IGNORED,
            getTestCategory(description)
        );
    }

    @Override
    protected void failed(final Throwable e,
                          final Description description) {
        jsonReportGenerator.report(
            description.getClassName(),
            description.getMethodName(),
            getTestDescription(description),
            ExceptionUtils.getStackTrace(e),
            TestResult.FAILURE,
            getTestCategory(description)
        );
    }

    private String getTestDescription(final Description description) {
        final TestDescription testDescription = description.getAnnotation(TestDescription.class);
        if (testDescription != null) {
            return testDescription.value();
        } else {
            return EMPTY;
        }
    }

    private TestCategory getTestCategory(final Description description) {
        final Category category = description.getAnnotation(Category.class);
        if (category != null) {
            return TestCategory.getForCategory(category);
        } else {
            return TestCategory.AUTOMATED;
        }
    }
}