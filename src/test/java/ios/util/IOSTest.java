package ios.util;

import common.AppiumTest;
import common.PlatformSpecificInstructions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static common.SharedConstants.TIMEOUT_MILLISECONDS;
import static ios.util.IOSAppiumConstants.*;

public abstract class IOSTest extends AppiumTest<IOSDriver<MobileElement>> {

    @Override
    protected IOSDriver<MobileElement> createDriver() throws Exception {
        final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME_ATTRIBUTE_KEY, PLATFORM_NAME_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(DEVICE_NAME_ATTRIBUTE_KEY, DEVICE_NAME_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(AUTOMATION_NAME_ATTRIBUTE_KEY, AUTOMATION_NAME_ATTRIBUTE_VALUE);
        desiredCapabilities.setCapability(BUNDLE_ID_ATTRIBUTE_KEY, BUNDLE_ID_ATTRIBUTE_VALUE);
        final IOSDriver<MobileElement> driver = new IOSDriver<>(new URL(DRIVER_URL), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        return driver;
    }

    @Override
    protected PlatformSpecificInstructions<IOSDriver<MobileElement>> createPlatformSpecificInstructions() {
        return new IOSSpecificInstructions();
    }
}