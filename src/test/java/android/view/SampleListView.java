package android.view;

import org.openqa.selenium.By;

import static android.util.AndroidAppiumConstants.ID_PREFIX;

public final class SampleListView {

    public static final By LIST = By.id(ID_PREFIX + "sample_list_view");

    public static final By TEST_MODE_ENABLED_LABEL = By.id(ID_PREFIX + "test_mode_enabled_text_view");
    public static final By APP_LAUNCHES_LABEL = By.id(ID_PREFIX + "app_launches_text_view");
}