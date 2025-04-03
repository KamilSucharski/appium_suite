package common.util

import common.util.watcher.ReportTestWatcher
import common.util.watcher.ToastTestWatcher
import org.junit.Rule
import org.junit.rules.TestRule

abstract class AppiumTest {

    @JvmField
    @Rule
    val toastTestWatcher: TestRule = ToastTestWatcher(platformSpecificInstructions)

    @JvmField
    @Rule
    val reportTestWatcher: TestRule = ReportTestWatcher()

}