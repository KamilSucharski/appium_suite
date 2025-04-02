package common.util

import common.util.exception.ManualActionRequiredException
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import java.time.Duration

object ManualAction {

    /**
     * This will notify the tester that a manual action needs to be performed until the trigger element is found by
     * the driver
     */
    @Throws(Exception::class)
    fun perform(
        driver: AppiumDriver,
        platformSpecificInstructions: PlatformSpecificInstructions<out AppiumDriver>,
        triggerBy: By,
        message: String
    ) {
        driver
            .manage()
            .timeouts()
            .implicitlyWait(Duration.ofMillis(60000))
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