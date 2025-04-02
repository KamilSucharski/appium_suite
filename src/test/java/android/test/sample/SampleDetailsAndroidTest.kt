package android.test.sample

import android.util.AndroidTest
import android.view.SampleDetailsView
import android.view.SampleListView
import common.AssertUtils.ignoreTest
import common.ListUtils
import common.ManualAction
import common.junit.SemiAutomated
import common.junit.TestDescription
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.categories.Category

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
            driver = driver,
            listBy = SampleListView.LIST
        ) {
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