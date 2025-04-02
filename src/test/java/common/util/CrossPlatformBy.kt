package common.util

import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

data class CrossPlatformBy(
    val android: By,
    val ios: By
) : By() {

    override fun findElements(context: SearchContext?): MutableList<WebElement> {
        return when (platform) {
            Platform.Android -> android
            Platform.IOS -> ios
        }.findElements(context)
    }

}