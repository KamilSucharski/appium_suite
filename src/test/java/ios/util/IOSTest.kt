package ios.util

import common.AppiumTest
import common.PlatformSpecificInstructions
import common.SharedConstants.TIMEOUT_MILLISECONDS
import org.openqa.selenium.WebElement
import io.appium.java_client.ios.IOSDriver
import ios.util.IOSAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_KEY
import ios.util.IOSAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_VALUE
import ios.util.IOSAppiumConstants.BUNDLE_ID_ATTRIBUTE_KEY
import ios.util.IOSAppiumConstants.BUNDLE_ID_ATTRIBUTE_VALUE
import ios.util.IOSAppiumConstants.DEVICE_NAME_ATTRIBUTE_KEY
import ios.util.IOSAppiumConstants.DEVICE_NAME_ATTRIBUTE_VALUE
import ios.util.IOSAppiumConstants.DRIVER_URL
import ios.util.IOSAppiumConstants.PLATFORM_NAME_ATTRIBUTE_KEY
import ios.util.IOSAppiumConstants.PLATFORM_NAME_ATTRIBUTE_VALUE
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.time.Duration
import java.util.concurrent.TimeUnit

abstract class IOSTest : AppiumTest<IOSDriver>() {

    @Throws(Exception::class)
    override fun createDriver(): IOSDriver {
        val desiredCapabilities = DesiredCapabilities()
        desiredCapabilities.setCapability(PLATFORM_NAME_ATTRIBUTE_KEY, PLATFORM_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(DEVICE_NAME_ATTRIBUTE_KEY, DEVICE_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(AUTOMATION_NAME_ATTRIBUTE_KEY, AUTOMATION_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(BUNDLE_ID_ATTRIBUTE_KEY, BUNDLE_ID_ATTRIBUTE_VALUE)
        val driver = IOSDriver(URL(DRIVER_URL), desiredCapabilities)
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(TIMEOUT_MILLISECONDS))
        return driver
    }

    override fun createPlatformSpecificInstructions(): PlatformSpecificInstructions<IOSDriver> {
        return IOSSpecificInstructions()
    }

}