package android.util;

import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

public final class AndroidSDKResolver {

    private static final Map<String, Integer> sdkMap = new HashMap<String, Integer>() {{
        put("5.0", 21);
        put("5.1", 22);
        put("6.0", 23);
        put("7.0", 24);
        put("7.1", 25);
        put("8.0", 26);
        put("8.1", 27);
        put("9", 28);
        put("10", 29);
        put("11", 30);
        put("12", 31);
    }};

    public static int resolve(final AndroidDriver driver) {
        final String platformName = (String) driver.getCapabilities().getCapability("platformVersion");
        for (final Map.Entry<String, Integer> entry : sdkMap.entrySet()) {
            if (platformName.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }

        System.out.println("Android version not recognised: " + platformName);
        return 1;
    }
}