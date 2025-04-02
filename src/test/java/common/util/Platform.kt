package common.util

import android.util.AndroidAppiumConstants
import android.util.AndroidSpecificInstructions
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import ios.util.IOSAppiumConstants
import ios.util.IOSSpecificInstructions
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.time.Duration

sealed class Platform {

    abstract val internalDriver: AppiumDriver
    abstract val internalPlatformSpecificInstructions: PlatformSpecificInstructions<out AppiumDriver>

    data object Android : Platform() {
        override val internalDriver = DesiredCapabilities().let { desiredCapabilities ->
            desiredCapabilities.setCapability(
                SharedConstants.PLATFORM_NAME_ATTRIBUTE_KEY,
                AndroidAppiumConstants.PLATFORM_NAME_ATTRIBUTE_VALUE
            )
            desiredCapabilities.setCapability(
                SharedConstants.AUTOMATION_NAME_ATTRIBUTE_KEY,
                AndroidAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_VALUE
            )
            val driver = AndroidDriver(URL(SharedConstants.DRIVER_URL), desiredCapabilities)
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(SharedConstants.TIMEOUT_MILLISECONDS))
            driver
        }

        override val internalPlatformSpecificInstructions = AndroidSpecificInstructions()
    }

    data object IOS : Platform() {
        override val internalDriver = DesiredCapabilities().let { desiredCapabilities ->
            desiredCapabilities.setCapability(
                SharedConstants.PLATFORM_NAME_ATTRIBUTE_KEY,
                IOSAppiumConstants.PLATFORM_NAME_ATTRIBUTE_VALUE
            )
            desiredCapabilities.setCapability(
                SharedConstants.AUTOMATION_NAME_ATTRIBUTE_KEY,
                IOSAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_VALUE
            )
            val driver = IOSDriver(URL(IOSAppiumConstants.DRIVER_URL), desiredCapabilities)
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(SharedConstants.TIMEOUT_MILLISECONDS))
            driver
        }

        override val internalPlatformSpecificInstructions = IOSSpecificInstructions()
    }

}