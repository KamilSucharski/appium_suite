package android.test.sample;

import android.util.AndroidTest;
import android.view.SampleDetailsView;
import android.view.SampleListView;
import common.ListUtils;
import common.junit.TestDescription;
import io.appium.java_client.MobileElement;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public final class SampleDetailsAndroidTest extends AndroidTest {

    @Test
    @TestDescription("Navigate to first item")
    public void C004() throws Exception {
        platformSpecificInstructions.restartApplication();

        final MobileElement item = driver.findElements(SampleListView.LIST_ELEMENT_LABEL).get(0);
        final String expectedText = item.getText();
        item.click();
        assertEquals(
            expectedText,
            driver.findElement(SampleDetailsView.NAME_LABEL).getText()
        );
    }

    @Test
    @TestDescription("Navigate to item Z")
    public void C005() throws Exception {
        platformSpecificInstructions.restartApplication();

        final String expectedText = "Z";
        ListUtils.scrollToBottom(
            driver,
            SampleListView.LIST,
            () -> {
                final Optional<MobileElement> element = driver
                    .findElements(SampleListView.LIST_ELEMENT_LABEL)
                    .stream()
                    .filter(currentElement -> currentElement.getText().equals(expectedText))
                    .findFirst();

                if (element.isPresent()) {
                    element.get().click();
                    return true;
                } else {
                    return false;
                }
            }
        );

        assertEquals(
            expectedText,
            driver.findElement(SampleDetailsView.NAME_LABEL).getText()
        );
    }
}