package common.util

import io.appium.java_client.AppiumDriver

var platform: Platform = Platform.Android
//var platform: Platform = Platform.IOS

val driver: AppiumDriver get() = platform.internalDriver
val platformSpecificInstructions: PlatformSpecificInstructions<out AppiumDriver> get() = platform.internalPlatformSpecificInstructions