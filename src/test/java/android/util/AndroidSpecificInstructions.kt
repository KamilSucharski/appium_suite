package android.util

import common.PlatformSpecificInstructions
import io.appium.java_client.android.AndroidDriver

class AndroidSpecificInstructions : PlatformSpecificInstructions<AndroidDriver> {

    @Throws(Exception::class)
    override fun resetApplication() {
        ADBCommands.resetApplication()
    }

    @Throws(Exception::class)
    override fun restartApplication() {
        ADBCommands.restartApplication()
    }

    @Throws(Exception::class)
    override fun grantPermissions() {
        ADBCommands.grantPermissions()
    }

    @Throws(Exception::class)
    override fun revokePermissions() {
        ADBCommands.revokePermissions()
    }

    @Throws(Exception::class)
    override fun displayMessage(message: String) {
        ADBCommands.displayMessage(message)
    }

    @Throws(Exception::class)
    override fun vibrateDevice(duration: Long) {
        ADBCommands.vibrateDevice(duration)
    }

    @Throws(Exception::class)
    override fun copyApplicationFile() {
        // not needed
    }

    @Throws(Exception::class)
    override fun removeApplicationFile() {
        // not needed
    }

    override fun getApplicationName(driver: AndroidDriver): String {
        return driver.currentPackage!!
    }

    override fun getScreenName(driver: AndroidDriver): String {
        return driver.currentActivity()!!
    }

}