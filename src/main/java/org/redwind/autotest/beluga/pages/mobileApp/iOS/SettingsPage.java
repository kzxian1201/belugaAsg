package org.redwind.autotest.beluga.pages.mobileApp.iOS;

import org.openqa.selenium.By;

public class SettingsPage {
    public static final By DEVELOPER = By.xpath("//*[contains(@value,'Developer')]");
    public static final By DARK_APPEARANCE_TOGGLE = By.xpath("//*[contains(@type,'XCUIElementTypeSwitch') and contains(@label,'Dark Appearance')]");
    public static final By SETTINGS_PAGELAYOUT = By.xpath("//*[contains(@type,'XCUIElementTypeApplication')]");
}
