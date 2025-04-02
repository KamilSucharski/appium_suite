package common

import common.watcher.ReportTestWatcher
import common.watcher.ToastTestWatcher
import io.appium.java_client.AppiumDriver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

abstract class AppiumTest<T : AppiumDriver> {

    protected val platformSpecificInstructions by lazy { createPlatformSpecificInstructions() }
    protected lateinit var driver: T

    @JvmField
    @Rule
    val toastTestWatcher: TestRule = ToastTestWatcher(platformSpecificInstructions)
    @JvmField
    @Rule
    val reportTestWatcher: TestRule = ReportTestWatcher()

    @Throws(Exception::class)
    protected abstract fun createDriver(): T

    protected abstract fun createPlatformSpecificInstructions(): PlatformSpecificInstructions<T>

    @Before
    @Throws(Exception::class)
    fun before() {
        driver = createDriver()
    }

    @After
    fun after() {
        driver.quit()
    }

}