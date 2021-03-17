package common;

import common.exception.ManualActionRequiredException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.concurrent.TimeUnit;

public final class ManualAction {

    /**
     * This will notify the tester that a manual action needs to be performed until the trigger element is found by
     * the driver
     */
    public static void perform(
        final AppiumDriver<MobileElement> driver,
        final PlatformSpecificInstructions platformSpecificInstructions,
        final By triggerBy,
        final String message
    ) throws Exception {
        driver
            .manage()
            .timeouts()
            .implicitlyWait(60000, TimeUnit.MILLISECONDS);

        platformSpecificInstructions.displayMessage(message);
        platformSpecificInstructions.vibrateDevice(2000);

        try {
            driver.findElement(triggerBy);
        } catch (final NoSuchElementException e) {
            throw new ManualActionRequiredException();
        }

        platformSpecificInstructions.vibrateDevice(200);
    }
}