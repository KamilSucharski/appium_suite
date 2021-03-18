package android.util

import android.util.AndroidAppiumConstants.ANDROID_SCREENSHOT_PATH_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.ANDROID_SCREENSHOT_PATH_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.AUTOMATION_NAME_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.DEVICE_NAME_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.DEVICE_NAME_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.DRIVER_URL
import android.util.AndroidAppiumConstants.NATIVE_WEB_SCREENSHOT_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.NATIVE_WEB_SCREENSHOT_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.PLATFORM_NAME_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.PLATFORM_NAME_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.STARTUP_RETRIES_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.STARTUP_RETRIES_ATTRIBUTE_VALUE
import android.util.AndroidAppiumConstants.STARTUP_RETRY_INTERVAL_ATTRIBUTE_KEY
import android.util.AndroidAppiumConstants.STARTUP_RETRY_INTERVAL_ATTRIBUTE_VALUE
import common.AppiumTest
import common.PlatformSpecificInstructions
import common.SharedConstants
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.util.concurrent.TimeUnit

abstract class AndroidTest : AppiumTest<AndroidDriver<MobileElement>>() {

    @Throws(Exception::class)
    override fun createDriver(): AndroidDriver<MobileElement> {
        val desiredCapabilities = DesiredCapabilities()
        desiredCapabilities.setCapability(PLATFORM_NAME_ATTRIBUTE_KEY, PLATFORM_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(DEVICE_NAME_ATTRIBUTE_KEY, DEVICE_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(AUTOMATION_NAME_ATTRIBUTE_KEY, AUTOMATION_NAME_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(STARTUP_RETRIES_ATTRIBUTE_KEY, STARTUP_RETRIES_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(STARTUP_RETRY_INTERVAL_ATTRIBUTE_KEY, STARTUP_RETRY_INTERVAL_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(NATIVE_WEB_SCREENSHOT_ATTRIBUTE_KEY, NATIVE_WEB_SCREENSHOT_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(ANDROID_SCREENSHOT_PATH_ATTRIBUTE_KEY, ANDROID_SCREENSHOT_PATH_ATTRIBUTE_VALUE)
        desiredCapabilities.setCapability(PACKAGE_NAME_ATTRIBUTE_KEY, PACKAGE_NAME_ATTRIBUTE_VALUE)
        val driver = AndroidDriver<MobileElement>(URL(DRIVER_URL), desiredCapabilities)
        driver.manage().timeouts().implicitlyWait(SharedConstants.TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
        return driver
    }

    override fun createPlatformSpecificInstructions(): PlatformSpecificInstructions<AndroidDriver<MobileElement>> {
        return AndroidSpecificInstructions()
    }
}