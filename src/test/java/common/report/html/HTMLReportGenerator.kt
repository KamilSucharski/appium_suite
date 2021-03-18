package common.report.html

import com.google.gson.Gson
import common.junit.Helper
import common.report.ReportConstants
import common.report.ReportEntry
import common.report.TestCategory
import common.report.TestResult
import org.junit.Test
import org.junit.experimental.categories.Category
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class HTMLReportGenerator {

    private val gson = Gson()

    @Test
    @Category(Helper::class)
    @Throws(Exception::class)
    fun generateHTMLReport() {
        var template = getFileContent(ReportConstants.HTML_REPORT_FILE_TEMPLATE_FILE_PATH)
        val entries = gson.fromJson<List<ReportEntry>>(getFileContent(ReportConstants.RAW_REPORT_FILE_PATH), ReportConstants.SERIALIZATION_TYPE)
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
        var template = template
        val totalCount = entries.size.toLong()
        val successCount = entries.stream().filter { entry: ReportEntry -> entry.testResult == TestResult.SUCCESS }.count()
        val successPercent = 100 * successCount / totalCount
        val failureCount = entries.stream().filter { entry: ReportEntry -> entry.testResult == TestResult.FAILURE }.count()
        val failurePercent = 100 * failureCount / totalCount
        val ignoredCount = entries.stream().filter { entry: ReportEntry -> entry.testResult == TestResult.IGNORED }.count()
        val ignoredPercent = 100 * ignoredCount / totalCount
        template = template.replace("total_count_placeholder", totalCount.toString())
        template = template.replace("success_count_placeholder", successCount.toString())
        template = template.replace("success_percent_placeholder", successPercent.toString())
        template = template.replace("failure_count_placeholder", failureCount.toString())
        template = template.replace("failure_percent_placeholder", failurePercent.toString())
        template = template.replace("ignored_count_placeholder", ignoredCount.toString())
        template = template.replace("ignored_percent_placeholder", ignoredPercent.toString())
        return template
    }

    @Throws(Exception::class)
    private fun fillTemplateDetailsSection(template: String, entries: List<ReportEntry>): String {
        var filledTemplate = template
        val detailsBuilder = StringBuilder()
        val testSetTemplate = getFileContent(ReportConstants.HTML_REPORT_TEST_SET_TEMPLATE_FILE_PATH)
        val testTemplate = getFileContent(ReportConstants.HTML_REPORT_TEST_TEMPLATE_FILE_PATH)
        val semiAutomatedIconTemplate = getFileContent(ReportConstants.HTML_REPORT_ICON_SEMI_AUTOMATED_TEMPLATE_FILE_PATH)
        val helperIconTemplate = getFileContent(ReportConstants.HTML_REPORT_ICON_HELPER_TEMPLATE_FILE_PATH)
        val testSets: List<Map.Entry<String, List<ReportEntry>>> = entries
            .stream()
            .collect(Collectors.groupingBy { obj: ReportEntry -> obj.className })
            .entries
            .stream()
            .sorted { o1: Map.Entry<String, List<ReportEntry?>?>, o2: Map.Entry<String, List<ReportEntry?>?> -> o1.key.compareTo(o2.key) }
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
                }
                detailsBuilder.append(testTemplate
                    .replace("color_class_placeholder", entry.testResult.toString().toLowerCase())
                    .replace("test_name_placeholder".toRegex(), entry.methodName)
                    .replace("test_description_placeholder", entry.description)
                    .replace("test_icons_placeholder", testIconBuilder.toString())
                    .replace("test_result_placeholder", entry.testResult.toString())
                    .replace("error_placeholder", entry.error)
                    .replace("error_html_placeholder", entry.error.replace("\n".toRegex(), "<br/>").replace("\t".toRegex(), "&emsp;").replace("'".toRegex(), "`"))
                )
            }
        }
        filledTemplate = filledTemplate.replace("details_placeholder", detailsBuilder.toString())
        return filledTemplate
    }

    @Throws(Exception::class)
    private fun saveTemplateToReportFile(template: String) {
        val fileName = String.format(ReportConstants.HTML_REPORT_FILE_PATH_FORMAT, FILE_DATE_FORMAT.format(Date()))
        PrintWriter(File(fileName)).use { writer -> writer.print(template) }
    }

    companion object {
        private val HEADER_DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy")
        private val FILE_DATE_FORMAT = SimpleDateFormat("yyyy_MM_dd__HH_mm")
    }
}