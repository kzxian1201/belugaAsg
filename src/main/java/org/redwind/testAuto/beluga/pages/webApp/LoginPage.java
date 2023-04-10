package org.redwind.testAuto.beluga.pages.webApp;


import org.openqa.selenium.By;

public class LoginPage {
    public static final By USERNAME = By.xpath("//input[@placeholder='Username']");
    public static final By PASSWORD = By.xpath("//input[@placeholder='Password']");
    public static final By LOGIN_BUTTON = By.xpath("//input[@type='submit' and @value='Login']");
}
