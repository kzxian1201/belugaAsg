package org.redwind.autotest.beluga.steps.mobileSteps;

import io.cucumber.java.en.Given;
import org.redwind.autotest.beluga.pages.mobileApp.iOS.SettingsPage;
import org.redwind.autotest.beluga.utils.WrapperMethods;

import java.io.IOException;
import java.io.Serial;
import java.time.Duration;

public class IOSFunctions extends WrapperMethods {
    public IOSFunctions() throws IOException {
    }
    @Given("Open developer console")
    public void openDeveloperConsole() {
        waitForPresenceOfElementLocatedInMobile(SettingsPage.SETTINGS_PAGELAYOUT, Duration.ofSeconds(120));
        scrollDownInMobile(SettingsPage.SETTINGS_PAGELAYOUT);
        tap(SettingsPage.DEVELOPER);
    }
}
