package android.test.sample

import android.util.AndroidTest
import android.view.SampleDetailsView
import android.view.SampleListView
import common.ListUtils
import common.junit.TestDescription
import io.appium.java_client.MobileElement
import org.junit.Assert
import org.junit.Test

class SampleDetailsAndroidTest : AndroidTest() {
    @Test
    @TestDescription("Navigate to first item")
    @Throws(Exception::class)
    fun c004() {
        platformSpecificInstructions.restartApplication()
        val item = driver.findElements(SampleListView.LIST_ELEMENT_LABEL)[0]
        val expectedText = item.text
        item.click()
        Assert.assertEquals(
            expectedText,
            driver.findElement(SampleDetailsView.NAME_LABEL).text
        )
    }

    @Test
    @TestDescription("Navigate to item Z")
    @Throws(Exception::class)
    fun c005() {
        platformSpecificInstructions.restartApplication()
        val expectedText = "Z"
        ListUtils.scrollToBottom(
            driver,
            SampleListView.LIST
        ) {
            val element = driver
                .findElements(SampleListView.LIST_ELEMENT_LABEL)
                .stream()
                .filter { currentElement: MobileElement -> currentElement.text == expectedText }
                .findFirst()
            return@scrollToBottom if (element.isPresent) {
                element.get().click()
                true
            } else {
                false
            }
        }
        Assert.assertEquals(
            expectedText,
            driver.findElement(SampleDetailsView.NAME_LABEL).text
        )
    }
}