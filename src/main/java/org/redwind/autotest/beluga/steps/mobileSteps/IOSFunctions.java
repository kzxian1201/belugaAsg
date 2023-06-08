package org.redwind.autotest.beluga.steps.mobileSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.redwind.autotest.beluga.pages.mobileApp.iOS.DeveloperPage;
import org.redwind.autotest.beluga.pages.mobileApp.iOS.SettingsPage;
import org.redwind.autotest.beluga.utils.WrapperMethods;

import java.io.IOException;
import java.time.Duration;

public class IOSFunctions extends WrapperMethods {
    public IOSFunctions() throws IOException {
    }
    @Given("Open developer console")
    public void openDeveloperConsole() {
        waitForPresenceOfElementLocated(SettingsPage.SETTINGS_PAGELAYOUT, Duration.ofSeconds(120));
        scrollDownInMobile(SettingsPage.SETTINGS_PAGELAYOUT);
        clickOnElement(SettingsPage.DEVELOPER);
    }
    @And("Turn on the dark mode")
    public void darkMode() {
        String value = getValueFromElement(DeveloperPage.DARK_MODE_TOGGLE,"value");
        if(value.equalsIgnoreCase("1")) {
            logger.info("Dark mode is already turned on");
        } else {
            clickOnElement(DeveloperPage.DARK_MODE_TOGGLE);
        }
    }
}
