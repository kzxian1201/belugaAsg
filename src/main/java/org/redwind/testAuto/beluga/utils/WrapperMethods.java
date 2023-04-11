package org.redwind.testAuto.beluga.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.redwind.testAuto.beluga.configuration.DriverFactory;

import java.time.Duration;
import java.util.List;

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

    public void zoom(int zoomPercentage) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.body.style.zoom = '"+zoomPercentage+"%'");
    }
    public void scrollToElement(By locator) {
        driver=driverFactory.getCurrentDriver();
        try {
            Actions action = new Actions(driver);
            action.scrollToElement((WebElement) locator).build().perform();
        } catch (WebDriverException getException) {
            logger.error("Scroll to element failed %s",getException);
        }
    }

    public String getTextFromElement(By locator) {
        driver=driverFactory.getCurrentDriver();
        String text=null;
        try {
            text = driver.findElement(locator).getText();
        }catch (WebDriverException getException) {
            logger.error("Cannot get the Text %s",getException);
        }
        return text;
    }

    public String getValueFromElement(By locator,String name) {
        driver=driverFactory.getCurrentDriver();
        String value=null;
        try {
            value = driver.findElement(locator).getAttribute(name);
        } catch (WebDriverException getException) {
            logger.error("Cannot get the attribute value %s",getException);
        }
        return value;
    }

    public String getCurrentURL() {
        driver=driverFactory.getCurrentDriver();
        String text = null;
        try {
            text=driver.getCurrentUrl();
        } catch (WebDriverException getException) {
            logger.error("Cannot get the URL %s",getException);
        }
        return text;
    }

    public void refreshThePage() {
        driver = driverFactory.getCurrentDriver();
        try {
            driver.navigate().refresh();
        } catch (WebDriverException getException) {
            logger.error("Cannot refresh the webpage",getException);
        }
    }

    public void navigateBack() {
        driver =driverFactory.getCurrentDriver();
        try {
            driver.navigate().back();
        } catch (WebDriverException getException) {
            logger.error("Cannot navigate back in webpage %s", getException);
        }
    }

    public void clearText(By locator) {
        driver=driverFactory.getCurrentDriver();
        try {
            driver.findElement(locator).clear();
        } catch (WebDriverException getException) {
            logger.error("Cannot clear the Text %s", getException);
        }
    }

    public void waitForPresenceOfElementLocated(By locator, Duration timeoutInSeconds) {
        driver=driverFactory.getCurrentDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver,timeoutInSeconds);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (WebDriverException getException) {
            logger.error("Element is not loaded within the time", getException);
        }
    }

    public void waitForVisibilityOfElementLocated(By locator, Duration timeoutInSeconds) {
        driver=driverFactory.getCurrentDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver,timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverException getException) {
            logger.error("Element is not loaded within the time", getException);
        }
    }
    public void selectByValue(By locator, String value) {
        driver=driverFactory.getCurrentDriver();
        try{
            Select select= new Select(driver.findElement(locator));
            select.selectByValue(value);
        } catch (WebDriverException getException) {
            logger.error("value is not present in dropdown", getException);
        }
    }
    public void selectByVisibleText(By locator, String text) {
        driver=driverFactory.getCurrentDriver();
        try{
            Select select= new Select(driver.findElement(locator));
            select.selectByVisibleText(text);
        } catch (WebDriverException getException) {
            logger.error("Text is not present in dropdown", getException);
        }
    }

    public void selectByIndex(By locator, int index) {
        driver=driverFactory.getCurrentDriver();
        try{
            Select select= new Select(driver.findElement(locator));
            select.selectByIndex(index);
        } catch (WebDriverException getException) {
            logger.error("value is not present in dropdown", getException);
        }
    }

    public List<WebElement> getAllDropdownValues(By locator) {
        driver = driverFactory.getCurrentDriver();
        List<WebElement> options=null;
        try {
            Select select = new Select(driver.findElement(locator));
            options=select.getOptions();
        } catch (WebDriverException getException) {
            logger.error("options are not visible", getException);
        }
        return options;
    }

}
