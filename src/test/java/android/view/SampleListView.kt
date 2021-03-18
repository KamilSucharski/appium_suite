package android.view

import android.util.AndroidAppiumConstants
import org.openqa.selenium.By

object SampleListView {
    val TEST_MODE_ENABLED_LABEL: By = By.id(AndroidAppiumConstants.ID_PREFIX + "test_mode_enabled_text_view")
    val APP_LAUNCHES_LABEL: By = By.id(AndroidAppiumConstants.ID_PREFIX + "app_launches_text_view")
    val LIST: By = By.id(AndroidAppiumConstants.ID_PREFIX + "sample_list_view")
    val LIST_ELEMENT_LABEL: By = By.id("android:id/text1")
}