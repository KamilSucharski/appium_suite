package common.util

import common.util.ImageUtils.createScreenshotOfMiddleOfTheElement
import common.util.ImageUtils.imagesAreEqual
import common.util.SharedConstants.TIMEOUT_MILLISECONDS
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.Point
import org.openqa.selenium.interactions.Sequence
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
import java.awt.image.BufferedImage
import java.time.Duration

object ListUtils {

    @Synchronized
    @Throws(Exception::class)
    fun scrollToTop(driver: AppiumDriver, listBy: By) {
        scrollToLimit(driver, listBy, true, null)
    }

    @Synchronized
    @Throws(Exception::class)
    fun scrollToTop(driver: AppiumDriver, listBy: By, callback: () -> Boolean) {
        scrollToLimit(driver, listBy, true, callback)
    }

    @Synchronized
    @Throws(Exception::class)
    fun scrollToBottom(driver: AppiumDriver, listBy: By) {
        scrollToLimit(driver, listBy, false, null)
    }

    @Synchronized
    @Throws(Exception::class)
    fun scrollToBottom(driver: AppiumDriver, listBy: By, callback: () -> Boolean) {
        scrollToLimit(driver, listBy, false, callback)
    }

    @Synchronized
    @Throws(Exception::class)
    fun findElementWhileScrollingToTop(driver: AppiumDriver, listBy: By, elementBy: By): WebElement {
        return findElementWhileScrollingToLimit(driver, listBy, elementBy, true)
    }

    @Synchronized
    @Throws(Exception::class)
    fun findElementWhileScrollingToBottom(driver: AppiumDriver, listBy: By, elementBy: By): WebElement {
        return findElementWhileScrollingToLimit(driver, listBy, elementBy, false)
    }

    @Synchronized
    @Throws(Exception::class)
    private fun scrollToLimit(driver: AppiumDriver, listBy: By, toTop: Boolean, callback: (() -> Boolean)?) {
        val list = driver.findElement(listBy)
        val margin = list.size.height / 20
        val topOfTheList = list.location.y + margin
        val bottomOfTheList = list.location.y + list.size.height - margin
        val leftOfTheList = list.location.x + 1
        val startDragPoint = if (toTop) Point(leftOfTheList, topOfTheList) else Point(leftOfTheList, bottomOfTheList)
        val endDragPoint = if (toTop) Point(leftOfTheList, bottomOfTheList) else Point(leftOfTheList, topOfTheList)
        var previousListScreenshot: BufferedImage
        var nextListScreenshot: BufferedImage
        var screenshotsAreEqual: Boolean
        do {
            if (callback != null && callback()) {
                return
            }
            previousListScreenshot = createScreenshotOfMiddleOfTheElement(list)

            val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
            val sequence = Sequence(finger, 0)
                .addAction(
                    finger.createPointerMove(
                    Duration.ZERO,
                    PointerInput.Origin.viewport(),
                    startDragPoint.x,
                    startDragPoint.y
                )
                )
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(Pause(finger, Duration.ofMillis(200)))
                .addAction(
                    finger.createPointerMove(
                        Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),
                        endDragPoint.x,
                        endDragPoint.y
                    )
                )
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))

            driver.perform(listOf(sequence))
            Waiter.wait(driver, 500)

            nextListScreenshot = createScreenshotOfMiddleOfTheElement(list)
            screenshotsAreEqual = imagesAreEqual(previousListScreenshot, nextListScreenshot)
        } while (!screenshotsAreEqual)
    }

    @Synchronized
    @Throws(Exception::class)
    private fun findElementWhileScrollingToLimit(
        driver: AppiumDriver,
        listBy: By,
        elementBy: By,
        toTop: Boolean
    ): WebElement {
        val elementArray = arrayOfNulls<WebElement>(1)
        scrollToLimit(driver, listBy, toTop) {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500))
            try {
                elementArray[0] = driver.findElement(elementBy)
                return@scrollToLimit true
            } catch (ignored: NoSuchElementException) {
            }
            false
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(TIMEOUT_MILLISECONDS))
        return elementArray[0] ?: throw NoSuchElementException("Element not found on list")
    }

}