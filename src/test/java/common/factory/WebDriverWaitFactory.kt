package common.factory;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static common.SharedConstants.TIMEOUT_MILLISECONDS;

public class WebDriverWaitFactory {

    public static WebDriverWait create(final AppiumDriver driver) {
        return create(driver, TIMEOUT_MILLISECONDS);
    }

    public static WebDriverWait create(final AppiumDriver driver, final long timeoutMilliseconds) {
        return new WebDriverWait(driver, timeoutMilliseconds / 1000);
    }
}
