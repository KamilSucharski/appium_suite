package common

import common.SharedConstants.TIMEOUT_MILLISECONDS
import common.exception.ElementDidNotDisappearException
import common.factory.WebDriverWaitFactory.create
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.support.ui.ExpectedConditions
import java.util.concurrent.TimeUnit

object DriverUtils {

    /**
     * This will continue when element is not displayed.
     */
    @Throws(ElementDidNotDisappearException::class)
    fun continueWhenElementIsNotDisplayed(driver: AppiumDriver<MobileElement>, locator: By) {
        driver
            .manage()
            .timeouts()
            .implicitlyWait(1000, TimeUnit.MILLISECONDS)
        var retriesLeft = 30
        while (retriesLeft > 0) {
            retriesLeft--
            if (!isElementDisplayed(driver, locator)) {
                driver
                    .manage()
                    .timeouts()
                    .implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
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
            .implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
        throw ElementDidNotDisappearException()
    }

    fun continueWhenElementIsEnabled(driver: AppiumDriver<MobileElement>, locator: By) {
        val wait = create(driver)
        val nextButton = driver.findElement(locator)
        wait.until(ExpectedConditions.attributeToBe(nextButton, "enabled", "true"))
    }

    fun isElementDisplayed(driver: AppiumDriver<MobileElement>, locator: By): Boolean {
        return try {
            driver.findElement(locator).isDisplayed
        } catch (e: NoSuchElementException) {
            false
        } catch (e: StaleElementReferenceException) {
            false
        }
    }
}