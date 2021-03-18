package common.factory

import common.SharedConstants
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.support.ui.WebDriverWait

object WebDriverWaitFactory {

    @JvmOverloads
    fun create(
        driver: AppiumDriver<MobileElement>,
        timeoutMilliseconds: Long = SharedConstants.TIMEOUT_MILLISECONDS
    ): WebDriverWait {
        return WebDriverWait(driver, timeoutMilliseconds / 1000)
    }
}