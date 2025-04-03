package common.util.report

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

object ReportConstants {
    val SERIALIZATION_TYPE: Type = object : TypeToken<ArrayList<ReportEntry>>() {}.type
    const val REPORT_OUTPUT_DIRECTORY_PATH = "report"
    const val RAW_REPORT_FILE_PATH = "$REPORT_OUTPUT_DIRECTORY_PATH/test_results.json"
    const val HTML_REPORT_FILE_PATH_FORMAT = "$REPORT_OUTPUT_DIRECTORY_PATH/test_results_%s.html"
    private const val HTML_TEMPLATES_DIRECTORY_PATH = "src/test/java/common/util/report/html/template"
    const val HTML_REPORT_FILE_TEMPLATE_FILE_PATH = "$HTML_TEMPLATES_DIRECTORY_PATH/file_template.html"
    const val HTML_REPORT_TEST_SET_TEMPLATE_FILE_PATH = "$HTML_TEMPLATES_DIRECTORY_PATH/test_set_template.html"
    const val HTML_REPORT_TEST_TEMPLATE_FILE_PATH = "$HTML_TEMPLATES_DIRECTORY_PATH/test_template.html"
    const val HTML_REPORT_ICON_SEMI_AUTOMATED_TEMPLATE_FILE_PATH = "$HTML_TEMPLATES_DIRECTORY_PATH/icon_semi_automated_template.html"
    const val HTML_REPORT_ICON_HELPER_TEMPLATE_FILE_PATH = "$HTML_TEMPLATES_DIRECTORY_PATH/icon_helper_template.html"
}