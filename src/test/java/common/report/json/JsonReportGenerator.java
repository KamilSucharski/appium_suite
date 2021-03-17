package common.report.json;

import com.google.gson.Gson;
import common.report.ReportEntry;
import common.report.TestCategory;
import common.report.TestResult;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static common.report.ReportConstants.*;

public final class JsonReportGenerator {

    private final Gson gson = new Gson();

    public void report(final String className,
                       final String methodName,
                       final String description,
                       final String error,
                       final TestResult testResult,
                       final TestCategory testCategory) {
        try {
            final File reportFile = readReportFile();
            final List<ReportEntry> oldEntries = getEntries(reportFile);
            final List<ReportEntry> newEntries = addOrReplaceEntry(className, methodName, description, error, testResult, testCategory, oldEntries);
            saveEntriesToReportFile(reportFile, newEntries);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private File readReportFile() throws IOException {
        final File reportOutputDirectory = new File(REPORT_OUTPUT_DIRECTORY_PATH);
        if (!reportOutputDirectory.exists()) {
            reportOutputDirectory.mkdir();
        }

        final File reportFile = new File(RAW_REPORT_FILE_PATH);
        if (!reportFile.exists()) {
            reportFile.createNewFile();
        }
        return reportFile;
    }

    private List<ReportEntry> getEntries(final File reportFile) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        try (final Scanner scanner = new Scanner(reportFile)) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
        }
        final List<ReportEntry> entries = gson.fromJson(stringBuilder.toString(), SERIALIZATION_TYPE);
        return entries != null
            ? entries
            : new ArrayList<>();
    }

    private List<ReportEntry> addOrReplaceEntry(final String className,
                                                final String methodName,
                                                final String description,
                                                final String error,
                                                final TestResult testResult,
                                                final TestCategory testCategory,
                                                final List<ReportEntry> entries) {
        final List<ReportEntry> duplicateEntries = entries
            .stream()
            .filter(entry -> entry.getClassName().equals(className) && entry.getMethodName().equals(methodName))
            .collect(Collectors.toList());

        if (!duplicateEntries.isEmpty()) {
            entries.removeAll(duplicateEntries);
        }

        final ReportEntry entry = new ReportEntry(className, methodName, description, error, testResult, testCategory);
        entries.add(entry);
        return entries;
    }

    private void saveEntriesToReportFile(final File reportFile,
                                         final List<ReportEntry> entries) throws IOException {
        try (final PrintWriter writer = new PrintWriter(reportFile)) {
            writer.print(gson.toJson(entries));
        }
    }
}