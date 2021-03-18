package ios.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ios.util.IOSAppiumConstants.BUNDLE_ID_ATTRIBUTE_VALUE;
import static ios.util.IOSAppiumConstants.APP_NAME;

public final class SIMCTLCommands {

    private static final String START_APPLICATION_COMMAND = String.format(
        "xcrun simctl launch booted %s",
        BUNDLE_ID_ATTRIBUTE_VALUE
    );
    private static final String STOP_APPLICATION_COMMAND = String.format(
        "xcrun simctl terminate booted %s",
        BUNDLE_ID_ATTRIBUTE_VALUE
    );
    private static final String UNINSTALL_APPLICATION_COMMAND = String.format(
        "xcrun simctl uninstall booted %s",
        BUNDLE_ID_ATTRIBUTE_VALUE
    );
    private static final String INSTALL_APPLICATION_COMMAND = String.format(
        "xcrun simctl install booted ./%s",
        APP_NAME
    );
    private static final String COPY_APPLICATION_COMMAND = String.format(
        "cp -R `xcrun simctl get_app_container booted %s | awk '{gsub(/ /,\"*\")}1'` ./%s",
        BUNDLE_ID_ATTRIBUTE_VALUE,
        APP_NAME
    );
    private static final String REMOVE_APPLICATION_FILE_COMMAND = String.format(
        "rm -rf ./%s",
        APP_NAME
    );

    private static final String DISABLE_HARDWARE_KEYBOARD_COMMAND = "defaults write com.apple.iphonesimulator ConnectHardwareKeyboard -bool no";
    //TODO
    private static final String GRANT_PERMISSIONS_COMMAND = "";
    //TODO
    private static final String REVOKE_PERMISSIONS_COMMAND = "";

    //TODO
    private static final String GET_SYSTEM_VERSION_COMMAND = "";
    //TODO
    private static final String DISPLAY_MESSAGE_COMMAND = "";
    //TODO
    private static final String VIBRATE_DEVICE_COMMAND = "";
    //TODO
    private static final Map<Integer, String[]> IOS_PERMISSIONS_GROUPED = new HashMap<Integer, String[]>() {{
        put(0, new String[]{
            ""
        });
        put(29, new String[]{
            ""
        });
    }};

    public static void restartApplication() throws Exception {
        executeCommand(STOP_APPLICATION_COMMAND);
        executeCommand(START_APPLICATION_COMMAND);
    }

    public static void resetApplication() throws Exception {
        executeCommand(STOP_APPLICATION_COMMAND);
        executeCommand(UNINSTALL_APPLICATION_COMMAND);
        executeCommand(INSTALL_APPLICATION_COMMAND);
        executeCommand(START_APPLICATION_COMMAND);
        executeCommand(DISABLE_HARDWARE_KEYBOARD_COMMAND);
    }

    public static void copyApplicationFile() throws Exception {
        executeCommand(COPY_APPLICATION_COMMAND);
    }

    public static void removeApplicationFile() throws Exception {
        executeCommand(REMOVE_APPLICATION_FILE_COMMAND);
    }

    //TODO
    public static void grantPermissions() throws Exception {
        final int systemVersion = getSystemVersion();
        for (final Map.Entry<Integer, String[]> permissionGroup : IOS_PERMISSIONS_GROUPED.entrySet()) {
            if (permissionGroup.getKey() <= systemVersion) {
                for (final String permission : permissionGroup.getValue()) {
                    executeCommand(GRANT_PERMISSIONS_COMMAND + permission);
                }
            }
        }
    }

    //TODO
    public static void revokePermissions() throws Exception {
        final int systemVersion = getSystemVersion();
        for (final Map.Entry<Integer, String[]> permissionGroup : IOS_PERMISSIONS_GROUPED.entrySet()) {
            if (permissionGroup.getKey() <= systemVersion) {
                for (final String permission : permissionGroup.getValue()) {
                    executeCommand(REVOKE_PERMISSIONS_COMMAND + permission);
                }
            }
        }
    }

    //TODO
    public static int getSystemVersion() throws Exception {
        return Integer.parseInt(executeCommand(GET_SYSTEM_VERSION_COMMAND).get(0));
    }

    //TODO
    public static void displayMessage(final String message) throws Exception {
        final String encodedMessage = message.replaceAll(" ", "⠀");
        final String command = String.format(DISPLAY_MESSAGE_COMMAND, encodedMessage);
        executeCommand(command);
    }

    //TODO
    public static void vibrateDevice(final long duration) throws Exception {
        final String command = String.format(VIBRATE_DEVICE_COMMAND, duration);
        executeCommand(command);
    }

    private static List<String> executeCommand(final String command) throws Exception {
        System.out.println(String.format("SIMCTL/I : %s", command));
        String[] cmd = {"/bin/sh", "-c", command};
        final Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor(5000, TimeUnit.MILLISECONDS);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final List<String> response = new ArrayList<>();
        String buffer;
        while ((buffer = reader.readLine()) != null && !buffer.isEmpty()) {
            response.add(buffer);
            System.out.println(String.format("SIMCTL/O : %s", buffer));
        }

        return response;
    }
}