package common

import common.watcher.ReportTestWatcher
import common.watcher.ToastTestWatcher
import io.appium.java_client.MobileDriver
import io.appium.java_client.MobileElement
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

abstract class AppiumTest<T : MobileDriver<MobileElement>> {

    protected val platformSpecificInstructions by lazy { createPlatformSpecificInstructions() }
    protected lateinit var driver: T

    @Rule
    val toastTestWatcher: TestRule = ToastTestWatcher(platformSpecificInstructions)
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