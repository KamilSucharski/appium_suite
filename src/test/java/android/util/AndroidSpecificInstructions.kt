package android.util;

import common.PlatformSpecificInstructions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public final class AndroidSpecificInstructions extends PlatformSpecificInstructions<AndroidDriver<MobileElement>> {

    @Override
    public void resetApplication() throws Exception {
        ADBCommands.resetApplication();
    }

    @Override
    public void restartApplication() throws Exception {
        ADBCommands.restartApplication();
    }

    @Override
    public void grantPermissions() throws Exception {
        ADBCommands.grantPermissions();
    }

    @Override
    public void revokePermissions() throws Exception {
        ADBCommands.revokePermissions();
    }

    @Override
    public void displayMessage(final String message) throws Exception {
        ADBCommands.displayMessage(message);
    }

    @Override
    public void vibrateDevice(final long duration) throws Exception {
        ADBCommands.vibrateDevice(duration);
    }

    @Override
    public void copyApplicationFile() throws Exception {
        // not needed
    }

    @Override
    public void removeApplicationFile() throws Exception {
        // not needed
    }

    @Override
    public String getApplicationName(final AndroidDriver<MobileElement> driver) {
        return driver.getCurrentPackage();
    }

    @Override
    public String getScreenName(final AndroidDriver<MobileElement> driver) {
        return driver.currentActivity();
    }
}