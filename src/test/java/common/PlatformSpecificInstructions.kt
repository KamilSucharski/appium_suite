package common;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public abstract class PlatformSpecificInstructions<T extends MobileDriver<MobileElement>> {

    public abstract void resetApplication() throws Exception;

    public abstract void restartApplication() throws Exception;

    public abstract void grantPermissions() throws Exception;

    public abstract void revokePermissions() throws Exception;

    public abstract void displayMessage(final String message) throws Exception;

    public abstract void vibrateDevice(final long duration) throws Exception;

    public abstract void copyApplicationFile() throws Exception;

    public abstract void removeApplicationFile() throws Exception;

    public abstract String getApplicationName(final T driver);

    public abstract String getScreenName(final T driver);
}