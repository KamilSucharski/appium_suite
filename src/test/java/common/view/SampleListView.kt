package common.view

import android.util.AndroidAppiumConstants
import common.util.CrossPlatformBy
import org.openqa.selenium.By

object SampleListView {
    val TEST_MODE_ENABLED_LABEL = CrossPlatformBy(
        android = By.id(AndroidAppiumConstants.ID_PREFIX + "test_mode_enabled_text_view"),
        ios = By.id("todo")
    )

    val APP_LAUNCHES_LABEL = CrossPlatformBy(
        android = By.id(AndroidAppiumConstants.ID_PREFIX + "app_launches_text_view"),
        ios = By.id("todo")
    )

    val LIST = CrossPlatformBy(
        android = By.id(AndroidAppiumConstants.ID_PREFIX + "sample_list_view"),
        ios = By.id("todo")
    )

    val LIST_ELEMENT_LABEL = CrossPlatformBy(
        android = By.id("android:id/text1"),
        ios = By.id("todo")
    )
}