package org.redwind.autotest.beluga.pages.mobileApp.iOS;

import org.openqa.selenium.By;

public class DeveloperPage {
    private DeveloperPage(){}
    public static final By DARK_MODE_TOGGLE = By.xpath("//*[contains(@type,'XCUIElementTypeSwitch')]");
}
