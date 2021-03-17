package common.report.html;

import com.google.gson.Gson;
import common.junit.Helper;
import common.report.ReportEntry;
import common.report.TestResult;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static common.report.ReportConstants.*;

final public class HTMLReportGenerator {

    private final Gson gson = new Gson();
    private static final SimpleDateFormat HEADER_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd__HH_mm");

    @Test
    @Category(Helper.class)
    public void generateHTMLReport() throws Exception {
        String template = getFileContent(HTML_REPORT_FILE_TEMPLATE_FILE_PATH);
        final List<ReportEntry> entries = gson.fromJson(getFileContent(RAW_REPORT_FILE_PATH), SERIALIZATION_TYPE);
        template = fillTemplateSummarySection(template, entries);
        template = fillTemplateDetailsSection(template, entries);
        template = template.replaceAll("date_placeholder", HEADER_DATE_FORMAT.format(new Date()));
        saveTemplateToReportFile(template);
    }

    private String getFileContent(final String filePath) throws Exception {
        final File file = new File(filePath);
        final StringBuilder stringBuilder = new StringBuilder();
        try (final Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
        }
        return stringBuilder.toString();
    }

    private String fillTemplateSummarySection(String template, final List<ReportEntry> entries) {
        final long totalCount = entries.size();
        final long successCount = entries.stream().filter(entry -> entry.getTestResult() == TestResult.SUCCESS).count();
        final long successPercent = 100 * successCount / totalCount;
        final long failureCount = entries.stream().filter(entry -> entry.getTestResult() == TestResult.FAILURE).count();
        final long failurePercent = 100 * failureCount / totalCount;
        final long ignoredCount = entries.stream().filter(entry -> entry.getTestResult() == TestResult.IGNORED).count();
        final long ignoredPercent = 100 * ignoredCount / totalCount;

        template = template.replace("total_count_placeholder", String.valueOf(totalCount));
        template = template.replace("success_count_placeholder", String.valueOf(successCount));
        template = template.replace("success_percent_placeholder", String.valueOf(successPercent));
        template = template.replace("failure_count_placeholder", String.valueOf(failureCount));
        template = template.replace("failure_percent_placeholder", String.valueOf(failurePercent));
        template = template.replace("ignored_count_placeholder", String.valueOf(ignoredCount));
        template = template.replace("ignored_percent_placeholder", String.valueOf(ignoredPercent));

        return template;
    }

    private String fillTemplateDetailsSection(String template, final List<ReportEntry> entries) throws Exception {
        final StringBuilder detailsBuilder = new StringBuilder();
        final String testSetTemplate = getFileContent(HTML_REPORT_TEST_SET_TEMPLATE_FILE_PATH);
        final String testTemplate = getFileContent(HTML_REPORT_TEST_TEMPLATE_FILE_PATH);
        final String semiAutomatedIconTemplate = getFileContent(HTML_REPORT_ICON_SEMI_AUTOMATED_TEMPLATE_FILE_PATH);
        final String helperIconTemplate = getFileContent(HTML_REPORT_ICON_HELPER_TEMPLATE_FILE_PATH);

        final List<Map.Entry<String, List<ReportEntry>>> testSets = entries
            .stream()
            .collect(Collectors.groupingBy(ReportEntry::getClassName))
            .entrySet()
            .stream()
            .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
            .collect(Collectors.toList());

        for (final Map.Entry<String, List<ReportEntry>> testSet : testSets) {
            detailsBuilder.append(testSetTemplate.replace("test_set_name_placeholder", testSet.getKey()));

            final List<ReportEntry> tests = testSet
                .getValue()
                .stream()
                .sorted((o1, o2) -> o1.getMethodName().compareTo(o2.getMethodName()))
                .collect(Collectors.toList());

            for (final ReportEntry entry : tests) {
                final StringBuilder testIconBuilder = new StringBuilder();

                switch (entry.getTestCategory()) {
                    case SEMI_AUTOMATED: {
                        testIconBuilder.append(semiAutomatedIconTemplate);
                        break;
                    }
                    case HELPER: {
                        testIconBuilder.append(helperIconTemplate);
                        break;
                    }
                }

                detailsBuilder.append(testTemplate
                    .replace("color_class_placeholder", entry.getTestResult().toString().toLowerCase())
                    .replaceAll("test_name_placeholder", entry.getMethodName())
                    .replace("test_description_placeholder", entry.getDescription())
                    .replace("test_icons_placeholder", testIconBuilder.toString())
                    .replace("test_result_placeholder", entry.getTestResult().toString())
                    .replace("error_placeholder", entry.getError())
                    .replace("error_html_placeholder", entry.getError().replaceAll("\n", "<br/>").replaceAll("\t", "&emsp;").replaceAll("'", "`"))
                );
            }
        }

        template = template.replace("details_placeholder", detailsBuilder.toString());
        return template;
    }

    private void saveTemplateToReportFile(final String template) throws Exception {
        final String fileName = String.format(HTML_REPORT_FILE_PATH_FORMAT, FILE_DATE_FORMAT.format(new Date()));
        try (final PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.print(template);
        }
    }
}