package common.report;

public final class ReportEntry {

    private final String className;
    private final String methodName;
    private final String description;
    private final String error;
    private final TestResult testResult;
    private final TestCategory testCategory;

    public ReportEntry(final String className,
                       final String methodName,
                       final String description,
                       final String error,
                       final TestResult testResult,
                       final TestCategory testCategory) {
        this.className = className;
        this.methodName = methodName;
        this.description = description;
        this.error = error;
        this.testResult = testResult;
        this.testCategory = testCategory;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getDescription() {
        return description;
    }

    public String getError() {
        return error;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public TestCategory getTestCategory() {
        return testCategory;
    }
}