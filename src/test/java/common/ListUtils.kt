package common

import common.ImageUtils.createScreenshotOfMiddleOfTheElement
import common.ImageUtils.imagesAreEqual
import common.SharedConstants.TIMEOUT_MILLISECONDS
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

object ListUtils {

    @Synchronized
    @Throws(Exception::class)
    fun scrollToTop(driver: AppiumDriver<MobileElement>, listBy: By) {
        scrollToLimit(driver, listBy, true, null)
    }

    @Synchronized
    @Throws(Exception::class)
    fun scrollToTop(driver: AppiumDriver<MobileElement>, listBy: By, callback: () -> Boolean) {
        scrollToLimit(driver, listBy, true, callback)
    }

    @Synchronized
    @Throws(Exception::class)
    fun scrollToBottom(driver: AppiumDriver<MobileElement>, listBy: By) {
        scrollToLimit(driver, listBy, false, null)
    }

    @Synchronized
    @Throws(Exception::class)
    fun scrollToBottom(driver: AppiumDriver<MobileElement>, listBy: By, callback: () -> Boolean) {
        scrollToLimit(driver, listBy, false, callback)
    }

    @Synchronized
    @Throws(Exception::class)
    fun findElementWhileScrollingToTop(driver: AppiumDriver<MobileElement>, listBy: By, elementBy: By): MobileElement {
        return findElementWhileScrollingToLimit(driver, listBy, elementBy, true)
    }

    @Synchronized
    @Throws(Exception::class)
    fun findElementWhileScrollingToBottom(driver: AppiumDriver<MobileElement>, listBy: By, elementBy: By): MobileElement {
        return findElementWhileScrollingToLimit(driver, listBy, elementBy, false)
    }

    @Synchronized
    @Throws(Exception::class)
    private fun scrollToLimit(driver: AppiumDriver<MobileElement>, listBy: By, toTop: Boolean, callback: (() -> Boolean)?) {
        val list = driver.findElement(listBy)
        val margin = list.size.height / 20
        val topOfTheList = list.location.y + margin
        val bottomOfTheList = list.location.y + list.size.height - margin
        val leftOfTheList = list.location.x + 1
        val startDragPoint = if (toTop) PointOption.point(leftOfTheList, topOfTheList) else PointOption.point(leftOfTheList, bottomOfTheList)
        val endDragPoint = if (toTop) PointOption.point(leftOfTheList, bottomOfTheList) else PointOption.point(leftOfTheList, topOfTheList)
        var previousListScreenshot: BufferedImage
        var nextListScreenshot: BufferedImage
        var screenshotsAreEqual: Boolean
        do {
            if (callback != null && callback()) {
                return
            }
            previousListScreenshot = createScreenshotOfMiddleOfTheElement(list)
            MobileTouchAction(driver)
                .longPress(startDragPoint)
                .moveTo(endDragPoint)
                .release()
                .perform()
            nextListScreenshot = createScreenshotOfMiddleOfTheElement(list)
            screenshotsAreEqual = imagesAreEqual(previousListScreenshot, nextListScreenshot)
        } while (!screenshotsAreEqual)
    }

    @Synchronized
    @Throws(Exception::class)
    private fun findElementWhileScrollingToLimit(driver: AppiumDriver<MobileElement>,
                                                 listBy: By,
                                                 elementBy: By,
                                                 toTop: Boolean): MobileElement {
        val elementArray = arrayOfNulls<MobileElement>(1)
        scrollToLimit(driver, listBy, toTop) {
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS)
            try {
                elementArray[0] = driver.findElement(elementBy)
                return@scrollToLimit true
            } catch (ignored: NoSuchElementException) {
            }
            false
        }
        driver.manage().timeouts().implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
        return elementArray[0] ?: throw NoSuchElementException("Element not found on list")
    }
}