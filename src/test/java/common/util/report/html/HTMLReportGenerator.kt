package common.util.report.html

import com.google.gson.Gson
import common.util.junit.Helper
import common.util.report.ReportConstants
import common.util.report.ReportEntry
import common.util.report.TestCategory
import common.util.report.TestResult
import org.junit.Test
import org.junit.experimental.categories.Category
import java.awt.Desktop
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class HTMLReportGenerator {

    companion object {
        private val HEADER_DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy")
        private val FILE_DATE_FORMAT = SimpleDateFormat("yyyy_MM_dd__HH_mm")
    }

    private val gson = Gson()

    @Test
    @Category(Helper::class)
    @Throws(Exception::class)
    fun generateHTMLReport() {
        var template = getFileContent(ReportConstants.HTML_REPORT_FILE_TEMPLATE_FILE_PATH)
        val entries = gson.fromJson<List<ReportEntry>>(
            getFileContent(ReportConstants.RAW_REPORT_FILE_PATH),
            ReportConstants.SERIALIZATION_TYPE
        )
        template = fillTemplateSummarySection(template, entries)
        template = fillTemplateDetailsSection(template, entries)
        template = template.replace("date_placeholder".toRegex(), HEADER_DATE_FORMAT.format(Date()))
        saveTemplateToReportFile(template)
    }

    @Throws(Exception::class)
    private fun getFileContent(filePath: String): String {
        val file = File(filePath)
        val stringBuilder = StringBuilder()
        Scanner(file).use { scanner ->
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine())
            }
        }
        return stringBuilder.toString()
    }

    private fun fillTemplateSummarySection(template: String, entries: List<ReportEntry>): String {
        var filledTemplate = template
        val totalCount = entries.size.toLong()
        val successCount =
            entries.stream().filter { entry: ReportEntry -> entry.testResult == TestResult.SUCCESS }.count()
        val successPercent = 100 * successCount / totalCount
        val failureCount =
            entries.stream().filter { entry: ReportEntry -> entry.testResult == TestResult.FAILURE }.count()
        val failurePercent = 100 * failureCount / totalCount
        val ignoredCount =
            entries.stream().filter { entry: ReportEntry -> entry.testResult == TestResult.IGNORED }.count()
        val ignoredPercent = 100 * ignoredCount / totalCount
        filledTemplate = filledTemplate.replace("total_count_placeholder", totalCount.toString())
        filledTemplate = filledTemplate.replace("success_count_placeholder", successCount.toString())
        filledTemplate = filledTemplate.replace("success_percent_placeholder", successPercent.toString())
        filledTemplate = filledTemplate.replace("failure_count_placeholder", failureCount.toString())
        filledTemplate = filledTemplate.replace("failure_percent_placeholder", failurePercent.toString())
        filledTemplate = filledTemplate.replace("ignored_count_placeholder", ignoredCount.toString())
        filledTemplate = filledTemplate.replace("ignored_percent_placeholder", ignoredPercent.toString())
        return filledTemplate
    }

    @Throws(Exception::class)
    private fun fillTemplateDetailsSection(template: String, entries: List<ReportEntry>): String {
        var filledTemplate = template
        val detailsBuilder = StringBuilder()
        val testSetTemplate = getFileContent(ReportConstants.HTML_REPORT_TEST_SET_TEMPLATE_FILE_PATH)
        val testTemplate = getFileContent(ReportConstants.HTML_REPORT_TEST_TEMPLATE_FILE_PATH)
        val semiAutomatedIconTemplate =
            getFileContent(ReportConstants.HTML_REPORT_ICON_SEMI_AUTOMATED_TEMPLATE_FILE_PATH)
        val helperIconTemplate = getFileContent(ReportConstants.HTML_REPORT_ICON_HELPER_TEMPLATE_FILE_PATH)
        val testSets: List<Map.Entry<String, List<ReportEntry>>> = entries
            .stream()
            .collect(Collectors.groupingBy { it.className })
            .entries
            .stream()
            .sorted { o1: Map.Entry<String, List<ReportEntry>>, o2: Map.Entry<String, List<ReportEntry>> ->
                o1.key.compareTo(
                    o2.key
                )
            }
            .collect(Collectors.toList())
        for ((key, value) in testSets) {
            detailsBuilder.append(testSetTemplate.replace("test_set_name_placeholder", key))
            val tests = value
                .stream()
                .sorted { o1: ReportEntry, o2: ReportEntry -> o1.methodName.compareTo(o2.methodName) }
                .collect(Collectors.toList())
            for (entry in tests) {
                val testIconBuilder = StringBuilder()
                when (entry.testCategory) {
                    TestCategory.SEMI_AUTOMATED -> {
                        testIconBuilder.append(semiAutomatedIconTemplate)
                    }

                    TestCategory.HELPER -> {
                        testIconBuilder.append(helperIconTemplate)
                    }

                    else -> {}
                }

                testTemplate
                    .replace(
                        "color_class_placeholder",
                        entry.testResult.toString().lowercase(Locale.getDefault())
                    )
                    .replace(
                        "test_name_placeholder".toRegex(),
                        entry.methodName
                    )
                    .replace(
                        "test_description_placeholder",
                        entry.description.replace("'".toRegex(), "`")
                    )
                    .replace(
                        "test_icons_placeholder",
                        testIconBuilder.toString()
                    )
                    .replace(
                        "test_result_placeholder",
                        entry.testResult.toString()
                    )
                    .replace(
                        "error_placeholder",
                        entry.error.replace("'".toRegex(), "`")
                    )
                    .replace(
                        "error_html_placeholder",
                        entry.error
                            .replace("\r\n".toRegex(), "<br/>")
                            .replace("\n".toRegex(), "<br/>")
                            .replace("\t".toRegex(), "&emsp;")
                            .replace("'".toRegex(), "`")
                    )
                    .let(detailsBuilder::append)

            }
        }
        filledTemplate = filledTemplate.replace("details_placeholder", detailsBuilder.toString())
        return filledTemplate
    }

    @Throws(Exception::class)
    private fun saveTemplateToReportFile(template: String) {
        val file = File(String.format(ReportConstants.HTML_REPORT_FILE_PATH_FORMAT, FILE_DATE_FORMAT.format(Date())))
        PrintWriter(file).use { writer -> writer.print(template) }
        Desktop.getDesktop().browse(file.toURI())
    }

}