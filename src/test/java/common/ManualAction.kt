package common

import common.exception.ManualActionRequiredException
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import java.util.concurrent.TimeUnit

object ManualAction {

    /**
     * This will notify the tester that a manual action needs to be performed until the trigger element is found by
     * the driver
     */
    @Throws(Exception::class)
    fun perform(
        driver: AppiumDriver<MobileElement>,
        platformSpecificInstructions: PlatformSpecificInstructions<*>,
        triggerBy: By,
        message: String
    ) {
        driver
            .manage()
            .timeouts()
            .implicitlyWait(60000, TimeUnit.MILLISECONDS)
        platformSpecificInstructions.displayMessage(message)
        platformSpecificInstructions.vibrateDevice(2000)
        try {
            driver.findElement(triggerBy)
        } catch (e: NoSuchElementException) {
            throw ManualActionRequiredException()
        }
        platformSpecificInstructions.vibrateDevice(200)
    }
}