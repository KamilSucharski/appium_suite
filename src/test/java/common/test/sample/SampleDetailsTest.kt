package common.test.sample

import common.util.*
import common.view.SampleDetailsView
import common.view.SampleListView
import common.util.AssertUtils.ignoreTest
import common.util.extension.scrollToBottom
import common.util.junit.SemiAutomated
import common.util.junit.TestDescription
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.categories.Category

class SampleDetailsTest : AppiumTest() {

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
        driver.scrollToBottom(SampleListView.LIST) {
            val element = driver
                .findElements(SampleListView.LIST_ELEMENT_LABEL)
                .stream()
                .filter { currentElement -> currentElement.text == expectedText }
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

    @Test
    @TestDescription("Navigate back")
    @Category(SemiAutomated::class)
    @Throws(Exception::class)
    fun c006() {
        platformSpecificInstructions.restartApplication()
        driver
            .findElements(SampleListView.LIST_ELEMENT_LABEL)[0]
            .click()
        driver.findElement(SampleDetailsView.NAME_LABEL)
        ManualAction.perform(
            driver = driver,
            platformSpecificInstructions = platformSpecificInstructions,
            triggerBy = SampleListView.TEST_MODE_ENABLED_LABEL,
            message = "Navigate back"
        )
    }

    @Test
    @TestDescription("Test ignoring sample")
    @Throws(Exception::class)
    fun c007() {
        ignoreTest("This is a test")
    }

}