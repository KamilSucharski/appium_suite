package common

import io.appium.java_client.AppiumDriver

object Waiter {

    @Throws(Exception::class)
    fun wait(driver: AppiumDriver, milliseconds: Long) {
        synchronized(driver) {
            (driver as Object).wait(milliseconds)
        }
    }
}