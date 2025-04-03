package common.util.extension

import common.util.SharedConstants
import common.util.Waiter
import common.util.exception.ElementDidNotDisappearException
import common.util.factory.WebDriverWaitFactory
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.support.ui.ExpectedConditions
import java.awt.image.BufferedImage
import java.time.Duration

/**
 * This will continue when element is not displayed.
 */
@Throws(ElementDidNotDisappearException::class)
fun AppiumDriver.continueWhenElementIsNotDisplayed(locator: By) {
    manage()
        .timeouts()
        .implicitlyWait(Duration.ofSeconds(1))

    var retriesLeft = 30
    while (retriesLeft > 0) {
        retriesLeft--
        if (!isElementDisplayed(locator)) {
            manage()
                .timeouts()
                .implicitlyWait(Duration.ofMillis(SharedConstants.TIMEOUT_SECONDS))
            return
        }
        try {
            Waiter.wait(this, 1000)
        } catch (ignored: Exception) {
        }
    }

    manage()
        .timeouts()
        .implicitlyWait(Duration.ofMillis(SharedConstants.TIMEOUT_SECONDS))

    throw ElementDidNotDisappearException()
}

fun AppiumDriver.continueWhenElementIsEnabled(locator: By) {
    val wait = WebDriverWaitFactory.create(this)
    val nextButton = findElement(locator)
    wait.until(ExpectedConditions.attributeToBe(nextButton, "enabled", "true"))
}

fun AppiumDriver.isElementDisplayed(locator: By): Boolean {
    return try {
        findElement(locator).isDisplayed
    } catch (e: NoSuchElementException) {
        false
    } catch (e: StaleElementReferenceException) {
        false
    }
}

@Throws(Exception::class)
fun AppiumDriver.scrollToTop(listBy: By) {
    scrollToLimit(listBy, true, null)
}

@Throws(Exception::class)
fun AppiumDriver.scrollToTop(listBy: By, callback: () -> Boolean) {
    scrollToLimit(listBy, true, callback)
}

@Throws(Exception::class)
fun AppiumDriver.scrollToBottom(listBy: By) {
    scrollToLimit(listBy, false, null)
}

@Throws(Exception::class)
fun AppiumDriver.scrollToBottom(listBy: By, callback: () -> Boolean) {
    scrollToLimit(listBy, false, callback)
}

@Throws(Exception::class)
fun AppiumDriver.findElementWhileScrollingToTop(listBy: By, elementBy: By): WebElement {
    return findElementWhileScrollingToLimit(listBy, elementBy, true)
}

@Throws(Exception::class)
fun AppiumDriver.findElementWhileScrollingToBottom(listBy: By, elementBy: By): WebElement {
    return findElementWhileScrollingToLimit(listBy, elementBy, false)
}

@Throws(Exception::class)
private fun AppiumDriver.scrollToLimit(listBy: By, toTop: Boolean, callback: (() -> Boolean)?) {
    val list = findElement(listBy)
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
        previousListScreenshot = list.createScreenshotOfMiddleOfTheElement()

        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
        val sequence = org.openqa.selenium.interactions.Sequence(finger, 0)
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

        perform(listOf(sequence))
        Waiter.wait(this, 500)

        nextListScreenshot = list.createScreenshotOfMiddleOfTheElement()
        screenshotsAreEqual = previousListScreenshot.isSameAs(nextListScreenshot)
    } while (!screenshotsAreEqual)
}

@Throws(Exception::class)
private fun AppiumDriver.findElementWhileScrollingToLimit(
    listBy: By,
    elementBy: By,
    toTop: Boolean
): WebElement {
    val elementArray = arrayOfNulls<WebElement>(1)
    scrollToLimit(listBy, toTop) {
        manage().timeouts().implicitlyWait(Duration.ofMillis(500))
        try {
            elementArray[0] = findElement(elementBy)
            return@scrollToLimit true
        } catch (ignored: NoSuchElementException) {
        }
        false
    }
    manage().timeouts().implicitlyWait(Duration.ofMillis(SharedConstants.TIMEOUT_SECONDS))
    return elementArray[0] ?: throw NoSuchElementException("Element not found on list")
}