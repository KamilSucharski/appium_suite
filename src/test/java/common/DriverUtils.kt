package common;

import common.exception.ElementDidNotDisappearException;
import common.factory.WebDriverWaitFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static common.SharedConstants.TIMEOUT_MILLISECONDS;

public final class DriverUtils {

    /**
     * This will continue when element is not displayed.
     */
    public static void continueWhenElementIsNotDisplayed(final AppiumDriver<MobileElement> driver,
                                                         final By locator) throws ElementDidNotDisappearException {
        driver
            .manage()
            .timeouts()
            .implicitlyWait(1000, TimeUnit.MILLISECONDS);

        int retriesLeft = 30;

        while (retriesLeft > 0) {
            retriesLeft--;
            if (!isElementDisplayed(driver, locator)) {
                driver
                    .manage()
                    .timeouts()
                    .implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
                return;
            }
            try {
                Waiter.wait(driver, 1000);
            } catch (final Exception ignored) {
            }
        }

        driver
            .manage()
            .timeouts()
            .implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        throw new ElementDidNotDisappearException();
    }

    public static void continueWhenElementIsEnabled(final AppiumDriver<MobileElement> driver,
                                                    final By locator) {
        final WebDriverWait wait = WebDriverWaitFactory.create(driver);
        final MobileElement nextButton = driver.findElement(locator);
        wait.until(ExpectedConditions.attributeToBe(nextButton, "enabled", "true"));
    }

    public static boolean isElementDisplayed(final AppiumDriver<MobileElement> driver,
                                             final By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
}