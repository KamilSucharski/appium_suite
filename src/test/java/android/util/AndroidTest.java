package android.util;

import common.AppiumTest;
import common.PlatformSpecificInstructions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static android.util.AndroidAppiumConstants.*;
import static common.SharedConstants.TIMEOUT_MILLISECONDS;

public abstract class AndroidTest extends AppiumTest<AndroidDriver<MobileElement>> {

    @Override
    protected AndroidDriver<MobileElement> createDriver() throws Exception {
        final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME_ATTRIBUTE_KEY, PLATFORM_NAME_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(DEVICE_NAME_ATTRIBUTE_KEY, DEVICE_NAME_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(AUTOMATION_NAME_ATTRIBUTE_KEY, AUTOMATION_NAME_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(STARTUP_RETRIES_ATTRIBUTE_KEY, STARTUP_RETRIES_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(STARTUP_RETRY_INTERVAL_ATTRIBUTE_KEY, STARTUP_RETRY_INTERVAL_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(NATIVE_WEB_SCREENSHOT_ATTRIBUTE_KEY, NATIVE_WEB_SCREENSHOT_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(ANDROID_SCREENSHOT_PATH_ATTRIBUTE_KEY, ANDROID_SCREENSHOT_PATH_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(PACKAGE_NAME_ATTRIBUTE_KEY, PACKAGE_NAME_ATTRIBUTE_VALUE);
        final AndroidDriver<MobileElement> driver = new AndroidDriver<>(new URL(DRIVER_URL), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        return driver;
    }

    @Override
    protected PlatformSpecificInstructions<AndroidDriver<MobileElement>> createPlatformSpecificInstructions() {
        return new AndroidSpecificInstructions();
    }
}