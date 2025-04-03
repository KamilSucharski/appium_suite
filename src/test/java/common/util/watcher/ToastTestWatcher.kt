package common.util.watcher

import common.util.PlatformSpecificInstructions
import io.appium.java_client.AppiumDriver
import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ToastTestWatcher(
    private val platformSpecificInstructions: PlatformSpecificInstructions<*>
) : TestWatcher() {

    companion object {
        private const val START_FORMAT = "%s starting..."
        private const val SUCCESS_FORMAT = "%s succeeded"
        private const val IGNORE_FORMAT = "%s ignored: %s"
        private const val FAILURE_FORMAT = "%s failed: %s"
    }

    override fun starting(description: Description) {
        displayMessageOnScreen(String.format(
            START_FORMAT,
            description.methodName
        ))
    }

    override fun succeeded(description: Description) {
        displayMessageOnScreen(String.format(
            SUCCESS_FORMAT,
            description.methodName
        ))
    }

    override fun skipped(e: AssumptionViolatedException, description: Description) {
        displayMessageOnScreen(String.format(
            IGNORE_FORMAT,
            description.methodName,
            e.javaClass.simpleName
        ))
    }

    override fun failed(e: Throwable, description: Description) {
        displayMessageOnScreen(String.format(
            FAILURE_FORMAT,
            description.methodName,
            e.javaClass.simpleName
        ))
    }

    private fun displayMessageOnScreen(message: String) {
        try {
            platformSpecificInstructions.displayMessage(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}