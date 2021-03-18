package android.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.util.AndroidAppiumConstants.PACKAGE_NAME_ATTRIBUTE_VALUE;
import static android.util.AndroidAppiumConstants.WEB_VIEW_PACKAGE_NAME;

public final class ADBCommands {

    private static final String START_APPLICATION_COMMAND = String.format(
        "adb shell monkey -p %s 1",
        PACKAGE_NAME_ATTRIBUTE_VALUE
    );
    private static final String STOP_APPLICATION_COMMAND = String.format(
        "adb shell am force-stop %s",
        PACKAGE_NAME_ATTRIBUTE_VALUE
    );
    private static final String STOP_CHROMIUM_COMMAND = String.format(
        "adb shell am force-stop %s",
        WEB_VIEW_PACKAGE_NAME
    );
    private static final String CLEAR_APPLICATION_COMMAND = String.format(
        "adb shell pm clear %s",
        PACKAGE_NAME_ATTRIBUTE_VALUE
    );
    private static final String GRANT_PERMISSIONS_COMMAND = String.format(
        "adb shell pm grant %s ",
        PACKAGE_NAME_ATTRIBUTE_VALUE
    );
    private static final String REVOKE_PERMISSIONS_COMMAND = String.format(
        "adb shell pm revoke %s ",
        PACKAGE_NAME_ATTRIBUTE_VALUE
    );
    private static final String GET_SYSTEM_VERSION_COMMAND = "adb shell getprop ro.build.version.sdk";
    private static final String DISPLAY_MESSAGE_COMMAND = "adb shell am broadcast -a com.sengami.appium_test_helper_android.toast --es message \"%s\" -n com.sengami.appium_test_helper_android/.ToastBroadcastReceiver";
    private static final String VIBRATE_DEVICE_COMMAND = "adb shell am broadcast -a com.sengami.appium_test_helper_android.vibration --el duration %d -n com.sengami.appium_test_helper_android/.VibrationBroadcastReceiver";
    private static final Map<Integer, String[]> ANDROID_PERMISSIONS_GROUPED_BY_API_LEVEL = new HashMap<Integer, String[]>() {{
        put(0, new String[] {
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        });
        put(29, new String[] {
            "android.permission.ACCESS_BACKGROUND_LOCATION"
        });
    }};

    public static void restartApplication() throws Exception {
        executeCommand(STOP_APPLICATION_COMMAND);
        executeCommand(STOP_CHROMIUM_COMMAND);
        executeCommand(START_APPLICATION_COMMAND);
    }

    public static void resetApplication() throws Exception {
        executeCommand(CLEAR_APPLICATION_COMMAND);
        executeCommand(START_APPLICATION_COMMAND);
    }

    public static void grantPermissions() throws Exception {
        final int systemVersion = getSystemVersion();
        for (final Map.Entry<Integer, String[]> permissionGroup : ANDROID_PERMISSIONS_GROUPED_BY_API_LEVEL.entrySet()) {
            if (permissionGroup.getKey() <= systemVersion) {
                for (final String permission : permissionGroup.getValue()) {
                    executeCommand(GRANT_PERMISSIONS_COMMAND + permission);
                }
            }
        }
    }

    public static void revokePermissions() throws Exception {
        final int systemVersion = getSystemVersion();
        for (final Map.Entry<Integer, String[]> permissionGroup : ANDROID_PERMISSIONS_GROUPED_BY_API_LEVEL.entrySet()) {
            if (permissionGroup.getKey() <= systemVersion) {
                for (final String permission : permissionGroup.getValue()) {
                    executeCommand(REVOKE_PERMISSIONS_COMMAND + permission);
                }
            }
        }
    }

    public static int getSystemVersion() throws Exception {
        return Integer.parseInt(executeCommand(GET_SYSTEM_VERSION_COMMAND).get(0));
    }

    public static void displayMessage(final String message) throws Exception {
        final String encodedMessage = message.replaceAll(" ", "\\\\ ");
        final String command = String.format(DISPLAY_MESSAGE_COMMAND, encodedMessage);
        executeCommand(command);
    }

    public static void vibrateDevice(final long duration) throws Exception {
        final String command = String.format(VIBRATE_DEVICE_COMMAND, duration);
        executeCommand(command);
    }

    private static List<String> executeCommand(final String command) throws Exception {
        System.out.println(String.format("ADB/I : %s", command));
        final Process process = Runtime.getRuntime().exec(command);
        process.waitFor(5000, TimeUnit.MILLISECONDS);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final List<String> response = new ArrayList<>();
        String buffer;
        while ((buffer = reader.readLine()) != null && !buffer.isEmpty()) {
            response.add(buffer);
            System.out.println(String.format("ADB/O : %s", buffer));
        }

        return response;
    }
}