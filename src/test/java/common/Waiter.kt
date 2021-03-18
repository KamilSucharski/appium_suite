package common

import io.appium.java_client.MobileDriver
import io.appium.java_client.MobileElement

object Waiter {

    @Throws(Exception::class)
    fun wait(driver: MobileDriver<MobileElement>, milliseconds: Long) {
        synchronized(driver) {
            (driver as Object).wait(milliseconds)
        }
    }
}