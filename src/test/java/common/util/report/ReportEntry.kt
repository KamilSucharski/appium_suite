package common.util.report

data class ReportEntry(
    val className: String,
    val methodName: String,
    val description: String,
    val error: String,
    val testResult: TestResult,
    val testCategory: TestCategory
)