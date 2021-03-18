package common.report;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

final public class ReportConstants {

    public static final Type SERIALIZATION_TYPE = new TypeToken<ArrayList<ReportEntry>>(){}.getType();
    public static final String REPORT_OUTPUT_DIRECTORY_PATH = "report";
    public static final String RAW_REPORT_FILE_PATH = REPORT_OUTPUT_DIRECTORY_PATH + "/test_results.json";
    public static final String HTML_REPORT_FILE_PATH_FORMAT = REPORT_OUTPUT_DIRECTORY_PATH + "/test_results_%s.html";
    private static final String HTML_TEMPLATES_DIRECTORY_PATH = "src/test/java/common/report/html/template";
    public static final String HTML_REPORT_FILE_TEMPLATE_FILE_PATH = HTML_TEMPLATES_DIRECTORY_PATH + "/file_template.html";
    public static final String HTML_REPORT_TEST_SET_TEMPLATE_FILE_PATH = HTML_TEMPLATES_DIRECTORY_PATH + "/test_set_template.html";
    public static final String HTML_REPORT_TEST_TEMPLATE_FILE_PATH = HTML_TEMPLATES_DIRECTORY_PATH + "/test_template.html";
    public static final String HTML_REPORT_ICON_SEMI_AUTOMATED_TEMPLATE_FILE_PATH = HTML_TEMPLATES_DIRECTORY_PATH + "/icon_semi_automated_template.html";
    public static final String HTML_REPORT_ICON_HELPER_TEMPLATE_FILE_PATH = HTML_TEMPLATES_DIRECTORY_PATH + "/icon_helper_template.html";
}