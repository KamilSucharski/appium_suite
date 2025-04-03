package common.test.sample

import common.util.AppiumTest
import common.view.SampleListView
import common.util.ListUtils
import common.util.driver
import common.util.junit.TestDescription
import common.util.platformSpecificInstructions
import org.junit.Assert
import org.junit.Test
import org.openqa.selenium.WebElement

class SampleListTest : AppiumTest() {

    @Test
    @TestDescription("Test mode is set to true")
    @Throws(Exception::class)
    fun c001() {
        platformSpecificInstructions.restartApplication()
        Assert.assertEquals(
            "true",
            driver.findElement(SampleListView.TEST_MODE_ENABLED_LABEL).text
        )
    }

    @Test
    @TestDescription("Number of app launches is counted correctly")
    @Throws(Exception::class)
    fun c002() {
        platformSpecificInstructions.resetApplication()
        Assert.assertEquals(
            "1",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).text
        )

        platformSpecificInstructions.restartApplication()
        Assert.assertEquals(
            "2",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).text
        )

        platformSpecificInstructions.restartApplication()
        Assert.assertEquals(
            "3",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).text
        )

        platformSpecificInstructions.resetApplication()
        Assert.assertEquals(
            "1",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).text
        )
    }

    @Test
    @TestDescription("List contains 26 elements")
    @Throws(Exception::class)
    fun c003() {
        platformSpecificInstructions.restartApplication()

        val foundItems = mutableSetOf<String>()
        ListUtils.scrollToBottom(
            driver,
            SampleListView.LIST
        ) {
            driver
                .findElements(SampleListView.LIST_ELEMENT_LABEL)
                .stream()
                .map(WebElement::getText)
                .forEach(foundItems::add)
            false
        }
        Assert.assertEquals(
            26,
            foundItems.size
        )
    }
}