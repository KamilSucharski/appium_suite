package common.util.factory

import common.util.SharedConstants
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

object WebDriverWaitFactory {

    @JvmOverloads
    fun create(
        driver: AppiumDriver,
        timeout: Duration = Duration.ofSeconds(SharedConstants.TIMEOUT_SECONDS)
    ): WebDriverWait {
        return WebDriverWait(driver, timeout)
    }

}