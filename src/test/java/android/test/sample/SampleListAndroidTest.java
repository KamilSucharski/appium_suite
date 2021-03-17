package android.test.sample;

import android.util.AndroidTest;
import android.view.SampleListView;
import common.junit.TestDescription;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class SampleListAndroidTest extends AndroidTest {

    @Test
    @TestDescription("Test mode is set to true")
    public void C001() throws Exception {
        platformSpecificInstructions.restartApplication();
        assertEquals(
            "true",
            driver.findElement(SampleListView.TEST_MODE_ENABLED_LABEL).getText()
        );
    }
}