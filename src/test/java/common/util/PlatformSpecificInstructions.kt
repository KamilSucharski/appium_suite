package common.util

import io.appium.java_client.AppiumDriver

interface PlatformSpecificInstructions<T : AppiumDriver> {
    @Throws(Exception::class)
    fun resetApplication()
    @Throws(Exception::class)
    fun restartApplication()
    @Throws(Exception::class)
    fun grantPermissions()
    @Throws(Exception::class)
    fun revokePermissions()
    @Throws(Exception::class)
    fun displayMessage(message: String)
    @Throws(Exception::class)
    fun vibrateDevice(duration: Long)
    @Throws(Exception::class)
    fun copyApplicationFile()
    @Throws(Exception::class)
    fun removeApplicationFile()
    fun getApplicationName(driver: T): String?
    fun getScreenName(driver: T): String?
}