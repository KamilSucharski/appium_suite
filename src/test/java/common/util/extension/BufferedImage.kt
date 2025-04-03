package common.util.extension

import java.awt.image.BufferedImage

private const val MINIMAL_IMAGE_MATCH_PERCENTAGE_TO_TREAT_AS_SAME_IMAGE = 99f

fun BufferedImage.isSameAs(
    other: BufferedImage,
    tolerance: Float = MINIMAL_IMAGE_MATCH_PERCENTAGE_TO_TREAT_AS_SAME_IMAGE
): Boolean {
    if (width != other.width || height != other.height) {
        return false
    }
    var allPixels = 0L
    var differentPixels = 0L
    for (x in 0 until width) {
        for (y in 0 until height) {
            allPixels++
            if (getRGB(x, y) != other.getRGB(x, y)) {
                differentPixels++
            }
        }
    }
    val matchPercentage = (allPixels - differentPixels).toFloat() / allPixels * 100
    println(String.format(
        "Similarity between first and second image: %.2f percent",
        matchPercentage
    ))
    return matchPercentage >= tolerance
}