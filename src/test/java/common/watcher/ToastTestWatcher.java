package common.watcher;

import common.PlatformSpecificInstructions;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ToastTestWatcher<T extends MobileDriver<MobileElement>> extends TestWatcher {

    private static final String START_FORMAT = "%s starting...";
    private static final String SUCCESS_FORMAT = "%s succeeded";
    private static final String IGNORE_FORMAT = "%s ignored: %s";
    private static final String FAILURE_FORMAT = "%s failed: %s";
    private final PlatformSpecificInstructions<T> platformSpecificInstructions;

    public ToastTestWatcher(final PlatformSpecificInstructions<T> platformSpecificInstructions) {
        this.platformSpecificInstructions = platformSpecificInstructions;
    }

    @Override
    protected void starting(final Description description) {
        displayMessageOnScreen(String.format(
            START_FORMAT,
            description.getMethodName()
        ));
    }

    @Override
    protected void succeeded(final Description description) {
        displayMessageOnScreen(String.format(
            SUCCESS_FORMAT,
            description.getMethodName()
        ));
    }

    @Override
    protected void skipped(final AssumptionViolatedException e,
                           final Description description) {
        displayMessageOnScreen(String.format(
            IGNORE_FORMAT,
            description.getMethodName(),
            e.getClass().getSimpleName()
        ));
    }

    @Override
    protected void failed(final Throwable e,
                          final Description description) {
        displayMessageOnScreen(String.format(
            FAILURE_FORMAT,
            description.getMethodName(),
            e.getClass().getSimpleName()
        ));
    }

    private void displayMessageOnScreen(final String message) {
        try {
            platformSpecificInstructions.displayMessage(message);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}