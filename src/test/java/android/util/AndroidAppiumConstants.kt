package android.util

object AndroidAppiumConstants {
    const val PLATFORM_NAME_ATTRIBUTE_KEY = "platformName"
    const val PLATFORM_NAME_ATTRIBUTE_VALUE = "Android"
    const val AUTOMATION_NAME_ATTRIBUTE_KEY = "appium:automationName"
    const val AUTOMATION_NAME_ATTRIBUTE_VALUE = "UiAutomator2"
    const val DRIVER_URL = "http://0.0.0.0:4723/"
    private const val APPLICATION_PACKAGE_NAME = "com.sengami.sample_testable_app_android"
    const val WEB_VIEW_PACKAGE_NAME = "com.android.chrome"
    const val PACKAGE_NAME_ATTRIBUTE_VALUE = APPLICATION_PACKAGE_NAME
    const val ID_PREFIX = "$APPLICATION_PACKAGE_NAME:id/"
}