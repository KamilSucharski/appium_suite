package ios.util

import common.PlatformSpecificInstructions
import org.openqa.selenium.WebElement
import io.appium.java_client.ios.IOSDriver

class IOSSpecificInstructions : PlatformSpecificInstructions<IOSDriver> {

    @Throws(Exception::class)
    override fun resetApplication() {
        SIMCTLCommands.resetApplication()
    }

    @Throws(Exception::class)
    override fun restartApplication() {
        SIMCTLCommands.restartApplication()
    }

    @Throws(Exception::class)
    override fun grantPermissions() {
    }

    @Throws(Exception::class)
    override fun revokePermissions() {
    }

    @Throws(Exception::class)
    override fun displayMessage(message: String) {
    }

    @Throws(Exception::class)
    override fun vibrateDevice(duration: Long) {
    }

    @Throws(Exception::class)
    override fun copyApplicationFile() {
        SIMCTLCommands.copyApplicationFile()
    }

    @Throws(Exception::class)
    override fun removeApplicationFile() {
        SIMCTLCommands.removeApplicationFile()
    }

    override fun getApplicationName(driver: IOSDriver): String {
        return "TODO"
    }

    override fun getScreenName(driver: IOSDriver): String {
        return "TODO"
    }

}