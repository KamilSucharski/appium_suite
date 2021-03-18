package common.factory

import common.SharedConstants
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.support.ui.WebDriverWait

object WebDriverWaitFactory {

    @JvmOverloads
    fun create(
        driver: AppiumDriver<*>,
        timeoutMilliseconds: Long = SharedConstants.TIMEOUT_MILLISECONDS.toLong()
    ): WebDriverWait {
        return WebDriverWait(driver, timeoutMilliseconds / 1000)
    }
}