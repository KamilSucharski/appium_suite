package common

import kotlin.Throws
import java.lang.Exception
import io.appium.java_client.MobileElement
import java.awt.image.BufferedImage
import common.ImageUtils
import org.openqa.selenium.OutputType
import javax.imageio.ImageIO

object ImageUtils {

    private const val MINIMAL_IMAGE_MATCH_PERCENTAGE_TO_TREAT_AS_SAME_IMAGE = 99f

    /**
     * We return only the middle 20% of the screenshot, because the borders can have bouncy effects, handles or progress
     * wheels
     */
    @JvmStatic
    @Throws(Exception::class)
    fun createScreenshotOfMiddleOfTheElement(element: MobileElement): BufferedImage {
        return createScreenshotOfTheElement(element, 20, 20, 40, 40)
    }

    @Throws(Exception::class)
    fun createScreenshotOfTheElement(
        element: MobileElement,
        percentOfWidthToInclude: Int,
        percentOfHeightToInclude: Int,
        percentOfWidthStart: Int,
        percentOfHeightStart: Int
    ): BufferedImage {
        val screenshotFile = element.getScreenshotAs(OutputType.FILE)
        val screenshot = ImageIO.read(screenshotFile)
        val width = screenshot.width * percentOfWidthToInclude / 100
        val height = screenshot.height * percentOfHeightToInclude / 100
        val x = screenshot.width * percentOfWidthStart / 100
        val y = screenshot.height * percentOfHeightStart / 100
        return screenshot.getSubimage(x, y, width, height)
    }

    @JvmStatic
    fun imagesAreEqual(image1: BufferedImage, image2: BufferedImage): Boolean {
        if (image1.width != image2.width || image1.height != image2.height) {
            return false
        }
        var allPixels = 0L
        var differentPixels = 0L
        for (x in 0 until image1.width) {
            for (y in 0 until image1.height) {
                allPixels++
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    differentPixels++
                }
            }
        }
        val matchPercentage = (allPixels - differentPixels).toFloat() / allPixels * 100
        println(String.format(
            "Similarity between first and second image: %.2f percent",
            matchPercentage
        ))
        return matchPercentage >= MINIMAL_IMAGE_MATCH_PERCENTAGE_TO_TREAT_AS_SAME_IMAGE
    }
}