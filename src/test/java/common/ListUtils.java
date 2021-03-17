package common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import static common.ImageUtils.createScreenshotOfMiddleOfTheElement;
import static common.ImageUtils.imagesAreEqual;
import static common.SharedConstants.TIMEOUT_MILLISECONDS;

public final class ListUtils {

    public synchronized static void scrollToTop(final AppiumDriver<MobileElement> driver,
                                                final By listBy) throws Exception {
        scrollToLimit(driver, listBy, true, null);
    }

    public synchronized static void scrollToTop(final AppiumDriver<MobileElement> driver,
                                                final By listBy,
                                                final ListScrollCallback callback) throws Exception {
        scrollToLimit(driver, listBy, true, callback);
    }

    public synchronized static void scrollToBottom(final AppiumDriver<MobileElement> driver,
                                                   final By listBy) throws Exception {
        scrollToLimit(driver, listBy, false, null);
    }

    public synchronized static void scrollToBottom(final AppiumDriver<MobileElement> driver,
                                                   final By listBy,
                                                   final ListScrollCallback callback) throws Exception {
        scrollToLimit(driver, listBy, false, callback);
    }

    public synchronized static MobileElement findElementWhileScrollingToTop(final AppiumDriver<MobileElement> driver,
                                                                            final By listBy,
                                                                            final By elementBy) throws Exception {
        return findElementWhileScrollingToLimit(driver, listBy, elementBy, true);
    }

    public synchronized static MobileElement findElementWhileScrollingToBottom(final AppiumDriver<MobileElement> driver,
                                                                               final By listBy,
                                                                               final By elementBy) throws Exception {
        return findElementWhileScrollingToLimit(driver, listBy, elementBy, false);
    }

    private synchronized static void scrollToLimit(final AppiumDriver<MobileElement> driver,
                                                   final By listBy,
                                                   final boolean toTop,
                                                   final ListScrollCallback callback) throws Exception {
        final MobileElement list = driver.findElement(listBy);
        final int margin = list.getSize().height / 20;
        final int topOfTheList = list.getLocation().y + margin;
        final int bottomOfTheList = list.getLocation().y + list.getSize().height - margin;
        final int leftOfTheList = list.getLocation().x + 1;

        final PointOption startDragPoint = toTop
            ? PointOption.point(leftOfTheList, topOfTheList)
            : PointOption.point(leftOfTheList, bottomOfTheList);
        final PointOption endDragPoint = toTop
            ? PointOption.point(leftOfTheList, bottomOfTheList)
            : PointOption.point(leftOfTheList, topOfTheList);

        BufferedImage previousListScreenshot;
        BufferedImage nextListScreenshot;
        boolean screeshotsAreEqual;

        do {
            if (callback != null && callback.doBeforeEachScroll()) {
                return;
            }

            previousListScreenshot = createScreenshotOfMiddleOfTheElement(list);

            new TouchAction(driver)
                .longPress(startDragPoint)
                .moveTo(endDragPoint)
                .release()
                .perform();

            nextListScreenshot = createScreenshotOfMiddleOfTheElement(list);
            screeshotsAreEqual = imagesAreEqual(previousListScreenshot, nextListScreenshot);
        } while (!screeshotsAreEqual);
    }

    private synchronized static MobileElement findElementWhileScrollingToLimit(final AppiumDriver<MobileElement> driver,
                                                                               final By listBy,
                                                                               final By elementBy,
                                                                               final boolean toTop) throws Exception {
        final MobileElement[] elementArray = new MobileElement[1];

        ListUtils.scrollToLimit(driver, listBy, toTop, () -> {
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            try {
                elementArray[0] = driver.findElement(elementBy);
                return true;
            } catch (final NoSuchElementException ignored) {
            }
            return false;
        });
        driver.manage().timeouts().implicitlyWait(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);

        final MobileElement element = elementArray[0];
        if (element == null) {
            throw new NoSuchElementException("Element not found on list");
        }

        return element;
    }

    @FunctionalInterface
    public interface ListScrollCallback {
        /**
         * If this returns true, it cancels further scrolling
         */
        boolean doBeforeEachScroll() throws Exception;
    }
}