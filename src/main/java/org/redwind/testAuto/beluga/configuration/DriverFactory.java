package org.redwind.testAuto.beluga.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
    public WebDriver driver = null;
    public WebDriver getDeskDriver(String browser) {
        if(browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(chromeOptions);
        } else if(browser.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriver();
        } else if(browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if(browser.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else if(browser.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        }
        return driver;
    }
}
