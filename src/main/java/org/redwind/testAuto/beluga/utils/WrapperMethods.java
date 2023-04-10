package org.redwind.testAuto.beluga.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.redwind.testAuto.beluga.configuration.DriverFactory;

public class WrapperMethods {

    private static Logger logger = LogManager.getFormatterLogger();
    private DriverFactory driverFactory = new DriverFactory();


    private WebDriver driver;

    public void openApplication(String url) {
        driver=driverFactory.getCurrentDriver();
        try {
            driver.get(url);
        } catch (WebDriverException getError) {
            logger.error("Failed to load the application because %s",getError);
        }
    }

    public void enterText(By locator, String value) {
        driver=driverFactory.getCurrentDriver();
        try {
            driver.findElement(locator).sendKeys(value);
        } catch (WebDriverException getError) {
            logger.error("Failed to enter Text %s",getError);
        }
    }

    public void clickOnElement(By locator) {
        driver=driverFactory.getCurrentDriver();
        try {
            driver.findElement(locator).click();
        } catch (WebDriverException getError) {
            logger.error("Failed to click on webElement %s", getError);
        }
    }
}
