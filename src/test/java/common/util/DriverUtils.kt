package common.util

import common.util.SharedConstants.TIMEOUT_MILLISECONDS
import common.util.exception.ElementDidNotDisappearException
import common.util.factory.WebDriverWaitFactory.create
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import java.util.concurrent.TimeUnit

object DriverUtils {

    /**
     * This will continue when element is not displayed.
     */
    @Throws(ElementDidNotDisappearException::class)
    fun continueWhenElementIsNotDisplayed(driver: AppiumDriver, locator: By) {
        driver
            .manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(1))

        var retriesLeft = 30
        while (retriesLeft > 0) {
            retriesLeft--
            if (!isElementDisplayed(driver, locator)) {
                driver
                    .manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofMillis(TIMEOUT_MILLISECONDS))
                return
            }
            try {
                Waiter.wait(driver, 1000)
            } catch (ignored: Exception) {
            }
        }

        driver
            .manage()
            .timeouts()
            .implicitlyWait(Duration.ofMillis(TIMEOUT_MILLISECONDS))

        throw ElementDidNotDisappearException()
    }

    fun continueWhenElementIsEnabled(driver: AppiumDriver, locator: By) {
        val wait = create(driver)
        val nextButton = driver.findElement(locator)
        wait.until(ExpectedConditions.attributeToBe(nextButton, "enabled", "true"))
    }

    fun isElementDisplayed(driver: AppiumDriver, locator: By): Boolean {
        return try {
            driver.findElement(locator).isDisplayed
        } catch (e: NoSuchElementException) {
            false
        } catch (e: StaleElementReferenceException) {
            false
        }
    }

}