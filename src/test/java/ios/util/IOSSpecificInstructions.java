package ios.util;

import common.PlatformSpecificInstructions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public final class IOSSpecificInstructions extends PlatformSpecificInstructions<IOSDriver<MobileElement>> {

    @Override
    public void resetApplication() throws Exception {
        SIMCTLCommands.resetApplication();
    }

    @Override
    public void restartApplication() throws Exception {
        SIMCTLCommands.restartApplication();
    }

    @Override
    public void grantPermissions() throws Exception {
    }

    @Override
    public void revokePermissions() throws Exception {
    }

    @Override
    public void displayMessage(final String message) throws Exception {
    }

    @Override
    public void vibrateDevice(final long duration) throws Exception {
    }

    @Override
    public void copyApplicationFile() throws Exception {
        SIMCTLCommands.copyApplicationFile();
    }

    @Override
    public void removeApplicationFile() throws Exception {
        SIMCTLCommands.removeApplicationFile();
    }

    @Override
    public String getApplicationName(final IOSDriver<MobileElement> driver) {
        return null;
    }

    @Override
    public String getScreenName(final IOSDriver<MobileElement> driver) {
        return null;
    }

}