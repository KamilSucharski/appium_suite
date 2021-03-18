package android.test.sample;

import android.util.AndroidTest;
import android.view.SampleDetailsView;
import android.view.SampleListView;
import common.ListUtils;
import common.junit.TestDescription;
import io.appium.java_client.MobileElement;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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

    @Test
    @TestDescription("Number of app launches is counted correctly")
    public void C002() throws Exception {
        platformSpecificInstructions.resetApplication();
        assertEquals(
            "1",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).getText()
        );

        platformSpecificInstructions.restartApplication();
        assertEquals(
            "2",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).getText()
        );

        platformSpecificInstructions.restartApplication();
        assertEquals(
            "3",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).getText()
        );

        platformSpecificInstructions.resetApplication();
        assertEquals(
            "1",
            driver.findElement(SampleListView.APP_LAUNCHES_LABEL).getText()
        );
    }

    @Test
    @TestDescription("List contains 26 elements")
    public void C003() throws Exception {
        platformSpecificInstructions.restartApplication();

        final Set<String> foundItems = new HashSet<>();
        ListUtils.scrollToBottom(
            driver,
            SampleListView.LIST,
            () -> {
                driver
                    .findElements(SampleListView.LIST_ELEMENT_LABEL)
                    .stream()
                    .map(MobileElement::getText)
                    .forEach(foundItems::add);
                return false;
            }
        );

        assertEquals(
            26,
            foundItems.size()
        );
    }
}