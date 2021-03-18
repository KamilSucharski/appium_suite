package android.util

object AndroidAppiumConstants {
    const val PLATFORM_NAME_ATTRIBUTE_KEY = "platformName"
    const val PLATFORM_NAME_ATTRIBUTE_VALUE = "Android"
    const val DEVICE_NAME_ATTRIBUTE_KEY = "deviceName"
    const val DEVICE_NAME_ATTRIBUTE_VALUE = "Android Device"
    const val AUTOMATION_NAME_ATTRIBUTE_KEY = "automationName"
    const val AUTOMATION_NAME_ATTRIBUTE_VALUE = "UiAutomator2"
    const val STARTUP_RETRIES_ATTRIBUTE_KEY = "wdaStartupRetries"
    const val STARTUP_RETRIES_ATTRIBUTE_VALUE = "4"
    const val STARTUP_RETRY_INTERVAL_ATTRIBUTE_KEY = "wdaStartupRetryInterval"
    const val STARTUP_RETRY_INTERVAL_ATTRIBUTE_VALUE = "20000"
    const val NATIVE_WEB_SCREENSHOT_ATTRIBUTE_KEY = "nativeWebScreenshot"
    const val NATIVE_WEB_SCREENSHOT_ATTRIBUTE_VALUE = "true"
    const val ANDROID_SCREENSHOT_PATH_ATTRIBUTE_KEY = "androidScreenshotPath"
    const val ANDROID_SCREENSHOT_PATH_ATTRIBUTE_VALUE = "target/screenshots"
    const val PACKAGE_NAME_ATTRIBUTE_KEY = "appWaitPackage"
    const val DRIVER_URL = "http://0.0.0.0:4723/wd/hub"
    private const val APPLICATION_PACKAGE_NAME = "com.sengami.sample_testable_app_android"
    const val WEB_VIEW_PACKAGE_NAME = "com.android.chrome"
    const val PACKAGE_NAME_ATTRIBUTE_VALUE = APPLICATION_PACKAGE_NAME
    const val ID_PREFIX = "$APPLICATION_PACKAGE_NAME:id/"
}