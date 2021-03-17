package common;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public final class ImageUtils {

    private static final float MINIMAL_IMAGE_MATCH_PERCENTAGE_TO_TREAT_AS_SAME_IMAGE = 99f;

    /**
     * We return only the middle 20% of the screenshot, because the borders can have bouncy effects, handles or progress
     * wheels
     */
    public static BufferedImage createScreenshotOfMiddleOfTheElement(final MobileElement element) throws Exception {
        return createScreenshotOfTheElement(element, 20, 20, 40, 40);
    }

    public static BufferedImage createScreenshotOfTheElement(final MobileElement element,
                                                             final int percentOfWidthToInclude,
                                                             final int percentOfHeightToInclude,
                                                             final int percentOfWidthStart,
                                                             final int percentOfHeightStart) throws Exception {
        final File screenshotFile = element.getScreenshotAs(OutputType.FILE);
        final BufferedImage screenshot = ImageIO.read(screenshotFile);
        final int width = screenshot.getWidth() * percentOfWidthToInclude / 100;
        final int height = screenshot.getHeight() * percentOfHeightToInclude / 100;
        final int x = screenshot.getWidth() * percentOfWidthStart / 100;
        final int y = screenshot.getHeight() * percentOfHeightStart / 100;
        return screenshot.getSubimage(x, y, width, height);
    }

    public static boolean imagesAreEqual(final BufferedImage image1,
                                         final BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }

        long allPixels = 0L;
        long differentPixels = 0L;

        for (int x = 0; x < image1.getWidth(); x++) {
            for (int y = 0; y < image1.getHeight(); y++) {
                allPixels++;
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    differentPixels++;
                }
            }
        }

        final float matchPercentage = (float) (allPixels - differentPixels) / allPixels * 100;
        System.out.println(String.format(
            "Similarity between first and second image: %.2f percent",
            matchPercentage
        ));
        return matchPercentage >= MINIMAL_IMAGE_MATCH_PERCENTAGE_TO_TREAT_AS_SAME_IMAGE;
    }
}