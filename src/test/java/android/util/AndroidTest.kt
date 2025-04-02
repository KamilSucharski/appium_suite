package android.util

import android.util.AndroidAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.DRIVER_URL
import android.util.AndroidAppiumConstants.PLATFORM_NAME_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.PLATFORM_NAME_ATTRIBUTE_VALUE
import common.AppiumTest
import common.PlatformSpecificInstructions
import common.SharedConstants
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.time.Duration

abstract class AndroidTest : AppiumTest<AndroidDriver>() {

    @Throws(Exception::class)
    override fun createDriver(): AndroidDriver {
        val desiredCapabilities = DesiredCapabilities()
        desiredCapabilities.setCapability(PLATFORM_NAME_ATTRIBUTE_KEY, PLATFORM_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(AUTOMATION_NAME_ATTRIBUTE_KEY, AUTOMATION_NAME_ATTRIBUTE_VALUE)
        val driver = AndroidDriver(URL(DRIVER_URL), desiredCapabilities)
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(SharedConstants.TIMEOUT_MILLISECONDS))
        return driver
    }

    override fun createPlatformSpecificInstructions(): PlatformSpecificInstructions<AndroidDriver> {
        return AndroidSpecificInstructions()
    }
}