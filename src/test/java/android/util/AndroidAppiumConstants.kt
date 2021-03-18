package android.util;

public final class AndroidAppiumConstants {

    public static final String PLATFORM_NAME_ATTRIBUTE_KEY = "platformName";
    public static final String PLATFORM_NAME_ATTRIBUTE_VALUE = "Android";
    public static final String DEVICE_NAME_ATTRIBUTE_KEY = "deviceName";
    public static final String DEVICE_NAME_ATTRIBUTE_VALUE = "Android Device";
    public static final String AUTOMATION_NAME_ATTRIBUTE_KEY = "automationName";
    public static final String AUTOMATION_NAME_ATTRIBUTE_VALUE = "UiAutomator2";
    public static final String STARTUP_RETRIES_ATTRIBUTE_KEY = "wdaStartupRetries";
    public static final String STARTUP_RETRIES_ATTRIBUTE_VALUE = "4";
    public static final String STARTUP_RETRY_INTERVAL_ATTRIBUTE_KEY = "wdaStartupRetryInterval";
    public static final String STARTUP_RETRY_INTERVAL_ATTRIBUTE_VALUE = "20000";
    public static final String NATIVE_WEB_SCREENSHOT_ATTRIBUTE_KEY = "nativeWebScreenshot";
    public static final String NATIVE_WEB_SCREENSHOT_ATTRIBUTE_VALUE = "true";
    public static final String ANDROID_SCREENSHOT_PATH_ATTRIBUTE_KEY = "androidScreenshotPath";
    public static final String ANDROID_SCREENSHOT_PATH_ATTRIBUTE_VALUE = "target/screenshots";
    public static final String PACKAGE_NAME_ATTRIBUTE_KEY = "appWaitPackage";
    public static final String DRIVER_URL = "http://0.0.0.0:4723/wd/hub";
    private static final String APPLICATION_PACKAGE_NAME = "com.sengami.sample_testable_app_android";
    public static final String WEB_VIEW_PACKAGE_NAME = "com.android.chrome";
    public static final String PACKAGE_NAME_ATTRIBUTE_VALUE = APPLICATION_PACKAGE_NAME;
    public static final String ID_PREFIX = APPLICATION_PACKAGE_NAME + ":id/";
}