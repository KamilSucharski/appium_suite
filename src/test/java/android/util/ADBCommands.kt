package android.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.TimeUnit

object ADBCommands {

    private val START_APPLICATION_COMMAND = String.format(
        "adb shell monkey -p %s 1",
        AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE
    )
    private val STOP_APPLICATION_COMMAND = String.format(
        "adb shell am force-stop %s",
        AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE
    )
    private val STOP_CHROMIUM_COMMAND = String.format(
        "adb shell am force-stop %s",
        AndroidAppiumConstants.WEB_VIEW_PACKAGE_NAME
    )
    private val CLEAR_APPLICATION_COMMAND = String.format(
        "adb shell pm clear %s",
        AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE
    )
    private val GRANT_PERMISSION_COMMAND = String.format(
        "adb shell pm grant %s ",
        AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE
    )
    private val REVOKE_PERMISSION_COMMAND = String.format(
        "adb shell pm revoke %s ",
        AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE
    )
    private const val GET_SYSTEM_VERSION_COMMAND = "adb shell getprop ro.build.version.sdk"
    private const val DISPLAY_MESSAGE_COMMAND = "adb shell am broadcast -a com.sengami.appium_test_helper_android.toast --es message \"%s\" -n com.sengami.appium_test_helper_android/.ToastBroadcastReceiver"
    private const val VIBRATE_DEVICE_COMMAND = "adb shell am broadcast -a com.sengami.appium_test_helper_android.vibration --el duration %d -n com.sengami.appium_test_helper_android/.VibrationBroadcastReceiver"
    private val ANDROID_PERMISSIONS_GROUPED_BY_API_LEVEL: Map<Int, Array<String>> = mapOf(
        0 to arrayOf(
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        ),
        29 to arrayOf(
            "android.permission.ACCESS_BACKGROUND_LOCATION"
        )
    )

    @Throws(Exception::class)
    fun restartApplication() {
        executeCommand(STOP_APPLICATION_COMMAND)
        executeCommand(STOP_CHROMIUM_COMMAND)
        executeCommand(START_APPLICATION_COMMAND)
    }

    @Throws(Exception::class)
    fun resetApplication() {
        executeCommand(CLEAR_APPLICATION_COMMAND)
        executeCommand(START_APPLICATION_COMMAND)
    }

    @Throws(Exception::class)
    fun grantPermissions() {
        val systemVersion = getSystemVersion()
        for ((key, value) in ANDROID_PERMISSIONS_GROUPED_BY_API_LEVEL) {
            if (key <= systemVersion) {
                for (permission in value) {
                    executeCommand(GRANT_PERMISSION_COMMAND + permission)
                }
            }
        }
    }

    @Throws(Exception::class)
    fun revokePermissions() {
        val systemVersion = getSystemVersion()
        for ((key, value) in ANDROID_PERMISSIONS_GROUPED_BY_API_LEVEL) {
            if (key <= systemVersion) {
                for (permission in value) {
                    executeCommand(REVOKE_PERMISSION_COMMAND + permission)
                }
            }
        }
    }

    @Throws(Exception::class)
    fun getSystemVersion(): Int {
        return executeCommand(GET_SYSTEM_VERSION_COMMAND)[0].toInt()
    }

    @Throws(Exception::class)
    fun displayMessage(message: String) {
        val encodedMessage = message.replace(" ".toRegex(), "\\\\ ")
        val command = String.format(DISPLAY_MESSAGE_COMMAND, encodedMessage)
        executeCommand(command)
    }

    @Throws(Exception::class)
    fun vibrateDevice(duration: Long) {
        val command = String.format(VIBRATE_DEVICE_COMMAND, duration)
        executeCommand(command)
    }

    @Throws(Exception::class)
    private fun executeCommand(command: String): List<String> {
        println("ADB/I : $command")
        val process = Runtime.getRuntime().exec(command)
        process.waitFor(5000, TimeUnit.MILLISECONDS)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val response: MutableList<String> = ArrayList()
        var buffer = ""
        while (reader.readLine()?.also { buffer = it } != null && buffer.isNotBlank()) {
            response.add(buffer)
            println("ADB/O : $buffer")
        }
        return response
    }
}