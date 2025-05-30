package android.util

import io.appium.java_client.android.AndroidDriver

object AndroidSDKResolver {

    private val sdkMap: Map<String, Int> = mapOf(
        "5.0" to 21,
        "5.1" to 22,
        "6.0" to 23,
        "7.0" to 24,
        "7.1" to 25,
        "8.0" to 26,
        "8.1" to 27,
        "9" to 28,
        "10" to 29,
        "11" to 30,
        "12" to 31,
        "12L" to 32,
        "13" to 33,
        "14" to 34,
        "15" to 35,
        "16" to 36
    )

    fun resolve(driver: AndroidDriver): Int {
        val platformName = driver.capabilities.getCapability("platformVersion") as String
        for ((key, value) in sdkMap) {
            if (platformName.startsWith(key)) {
                return value
            }
        }
        println("Android version not recognised: $platformName")
        return 1
    }
}