package common;

import io.appium.java_client.AppiumDriver;

public final class Waiter {

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public static void wait(final AppiumDriver driver, final long milliseconds) throws Exception {
        synchronized (driver) {
            driver.wait(milliseconds);
        }
    }
}