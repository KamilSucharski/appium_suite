package common.util.report.json

import com.google.gson.Gson
import common.util.report.ReportConstants
import common.util.report.ReportEntry
import common.util.report.TestCategory
import common.util.report.TestResult
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*
import java.util.stream.Collectors

class JsonReportGenerator {

    private val gson = Gson()

    fun report(
        className: String,
        methodName: String,
        description: String,
        error: String,
        testResult: TestResult,
        testCategory: TestCategory
    ) {
        try {
            val reportFile = readReportFile()
            val oldEntries = getEntries(reportFile)
            val newEntries = addOrReplaceEntry(
                className,
                methodName,
                description,
                error,
                testResult,
                testCategory,
                oldEntries
            )
            saveEntriesToReportFile(reportFile, newEntries)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun readReportFile(): File {
        val reportOutputDirectory = File(ReportConstants.REPORT_OUTPUT_DIRECTORY_PATH)
        if (!reportOutputDirectory.exists()) {
            reportOutputDirectory.mkdir()
        }
        val reportFile = File(ReportConstants.RAW_REPORT_FILE_PATH)
        if (!reportFile.exists()) {
            reportFile.createNewFile()
        }
        return reportFile
    }

    @Throws(IOException::class)
    private fun getEntries(reportFile: File): MutableList<ReportEntry> {
        val stringBuilder = StringBuilder()
        Scanner(reportFile).use { scanner ->
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine())
            }
        }
        val entries =
            gson.fromJson<MutableList<ReportEntry>>(stringBuilder.toString(), ReportConstants.SERIALIZATION_TYPE)
        return entries ?: ArrayList()
    }

    private fun addOrReplaceEntry(
        className: String,
        methodName: String,
        description: String,
        error: String,
        testResult: TestResult,
        testCategory: TestCategory,
        entries: MutableList<ReportEntry>
    ): List<ReportEntry> {
        val duplicateEntries = entries
            .stream()
            .filter { entry: ReportEntry -> entry.className == className && entry.methodName == methodName }
            .collect(Collectors.toList())
        if (duplicateEntries.isNotEmpty()) {
            entries.removeAll(duplicateEntries)
        }
        val entry = ReportEntry(className, methodName, description, error, testResult, testCategory)
        entries.add(entry)
        return entries
    }

    @Throws(IOException::class)
    private fun saveEntriesToReportFile(
        reportFile: File,
        entries: List<ReportEntry>
    ) {
        PrintWriter(reportFile).use { writer -> writer.print(gson.toJson(entries)) }
    }
}