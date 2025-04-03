package common.util.extension

import org.openqa.selenium.OutputType
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage
import java.lang.Exception
import javax.imageio.ImageIO

/**
 * We return only the middle 20% of the screenshot, because the borders can have bouncy effects, handles or progress
 * wheels
 */
@Throws(Exception::class)
fun WebElement.createScreenshotOfMiddleOfTheElement(): BufferedImage {
    return createScreenshotOfTheElement(
        20,
        20,
        40,
        40
    )
}

@Throws(Exception::class)
fun WebElement.createScreenshotOfTheElement(
    percentOfWidthToInclude: Int,
    percentOfHeightToInclude: Int,
    percentOfWidthStart: Int,
    percentOfHeightStart: Int
): BufferedImage {
    val screenshotFile = getScreenshotAs(OutputType.FILE)
    val screenshot = ImageIO.read(screenshotFile)
    val width = screenshot.width * percentOfWidthToInclude / 100
    val height = screenshot.height * percentOfHeightToInclude / 100
    val x = screenshot.width * percentOfWidthStart / 100
    val y = screenshot.height * percentOfHeightStart / 100
    return screenshot.getSubimage(x, y, width, height)
}