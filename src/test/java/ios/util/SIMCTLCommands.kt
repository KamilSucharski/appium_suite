package ios.util

import ios.util.IOSAppiumConstants.APP_NAME
import ios.util.IOSAppiumConstants.BUNDLE_ID_ATTRIBUTE_VALUE
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.TimeUnit

object SIMCTLCommands {

    private val START_APPLICATION_COMMAND = String.format(
        "xcrun simctl launch booted %s",
        BUNDLE_ID_ATTRIBUTE_VALUE
    )
    private val STOP_APPLICATION_COMMAND = String.format(
        "xcrun simctl terminate booted %s",
        BUNDLE_ID_ATTRIBUTE_VALUE
    )
    private val UNINSTALL_APPLICATION_COMMAND = String.format(
        "xcrun simctl uninstall booted %s",
        BUNDLE_ID_ATTRIBUTE_VALUE
    )
    private val INSTALL_APPLICATION_COMMAND = String.format(
        "xcrun simctl install booted ./%s",
        APP_NAME
    )
    private val COPY_APPLICATION_COMMAND = String.format(
        "cp -R `xcrun simctl get_app_container booted %s | awk '{gsub(/ /,\"*\")}1'` ./%s",
        BUNDLE_ID_ATTRIBUTE_VALUE,
        APP_NAME
    )
    private val REMOVE_APPLICATION_FILE_COMMAND = String.format(
        "rm -rf ./%s",
        APP_NAME
    )
    private const val DISABLE_HARDWARE_KEYBOARD_COMMAND = "defaults write com.apple.iphonesimulator ConnectHardwareKeyboard -bool no"

    //TODO
    private const val GRANT_PERMISSIONS_COMMAND = ""

    //TODO
    private const val REVOKE_PERMISSIONS_COMMAND = ""

    //TODO
    private const val GET_SYSTEM_VERSION_COMMAND = ""

    //TODO
    private const val DISPLAY_MESSAGE_COMMAND = ""

    //TODO
    private const val VIBRATE_DEVICE_COMMAND = ""

    @Throws(Exception::class)
    fun restartApplication() {
        executeCommand(STOP_APPLICATION_COMMAND)
        executeCommand(START_APPLICATION_COMMAND)
    }

    @Throws(Exception::class)
    fun resetApplication() {
        executeCommand(STOP_APPLICATION_COMMAND)
        executeCommand(UNINSTALL_APPLICATION_COMMAND)
        executeCommand(INSTALL_APPLICATION_COMMAND)
        executeCommand(START_APPLICATION_COMMAND)
        executeCommand(DISABLE_HARDWARE_KEYBOARD_COMMAND)
    }

    @Throws(Exception::class)
    fun copyApplicationFile() {
        executeCommand(COPY_APPLICATION_COMMAND)
    }

    @Throws(Exception::class)
    fun removeApplicationFile() {
        executeCommand(REMOVE_APPLICATION_FILE_COMMAND)
    }

    //TODO
    @Throws(Exception::class)
    fun grantPermissions() {

    }

    //TODO
    @Throws(Exception::class)
    fun revokePermissions() {

    }

    //TODO
    @Throws(Exception::class)
    fun getSystemVersion(): Int {
        return executeCommand(GET_SYSTEM_VERSION_COMMAND)[0].toInt()
    }

    //TODO
    @Throws(Exception::class)
    fun displayMessage(message: String) {
        val encodedMessage = message.replace(" ".toRegex(), "⠀")
        val command = String.format(DISPLAY_MESSAGE_COMMAND, encodedMessage)
        executeCommand(command)
    }

    //TODO
    @Throws(Exception::class)
    fun vibrateDevice(duration: Long) {
        val command = String.format(VIBRATE_DEVICE_COMMAND, duration)
        executeCommand(command)
    }

    @Throws(Exception::class)
    private fun executeCommand(command: String): List<String> {
        println(String.format("SIMCTL/I : %s", command))
        val cmd = arrayOf("/bin/sh", "-c", command)
        val process = Runtime.getRuntime().exec(cmd)
        process.waitFor(5000, TimeUnit.MILLISECONDS)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val response: MutableList<String> = ArrayList()
        var buffer: String
        while (reader.readLine().also { buffer = it } != null && !buffer.isEmpty()) {
            response.add(buffer)
            println(String.format("SIMCTL/O : %s", buffer))
        }
        return response
    }
}