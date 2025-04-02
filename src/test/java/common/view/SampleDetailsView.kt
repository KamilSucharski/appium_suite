package common.view

import android.util.AndroidAppiumConstants
import common.util.CrossPlatformBy
import org.openqa.selenium.By

object SampleDetailsView {
    val NAME_LABEL = CrossPlatformBy(
        android = By.id(AndroidAppiumConstants.ID_PREFIX + "sample_name_text_view"),
        ios = By.id("todo")
    )
}