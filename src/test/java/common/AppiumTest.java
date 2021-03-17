package common;

import common.watcher.ReportTestWatcher;
import common.watcher.ToastTestWatcher;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;

public abstract class AppiumTest<T extends MobileDriver<MobileElement>> {

    protected T driver;
    protected PlatformSpecificInstructions<T> platformSpecificInstructions = createPlatformSpecificInstructions();
    @Rule
    public final TestRule toastTestWatcher = new ToastTestWatcher<>(platformSpecificInstructions);
    @Rule
    public final TestRule reportTestWatcher = new ReportTestWatcher();

    protected abstract T createDriver() throws Exception;

    protected abstract PlatformSpecificInstructions<T> createPlatformSpecificInstructions();

    @Before
    public void before() throws Exception {
        driver = createDriver();
    }

    @After
    public void after() {
        driver.quit();
    }
}