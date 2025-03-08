package org.redwind.autotest.beluga.configuration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.redwind.autotest.beluga.utils.Environment;
import org.redwind.autotest.beluga.utils.PropertyReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static ThreadLocal<WebDriver> currentDriver = new ThreadLocal<>();
    private static ThreadLocal<AppiumDriver> currentAppiumDriver = new ThreadLocal<>();
    public WebDriver driver = null;
    public AppiumDriver appiumDriver =null;
    private Logger logger = LogManager.getFormatterLogger();
    private PropertyReader propertyReader = new PropertyReader();
    Environment environment;
    DesiredCapabilities capabilities = new DesiredCapabilities();

    public DriverFactory() {
        environment = propertyReader.getEnvironment();
    }


    public WebDriver getCurrentDriver() {
        driver = currentDriver.get();
        if(driver!=null) {
            return driver;
        } else {
            logger.info("Driver is not initiated");
            return null;
        }
    }
    public AppiumDriver getCurrentAppiumDriver() {
        appiumDriver =currentAppiumDriver.get();
        if(appiumDriver!=null) {
            return appiumDriver;
        } else {
            logger.info("Appium Driver is not initialized");
            return null;
        }
    }

    public void initializeBrowser() {
        currentDriver.set(getDesktopDriver(environment.getPlatform()));
    }
    public void initializeMobileDriver() throws IOException, InterruptedException {
        currentAppiumDriver.set(getAppiumDriver(environment.getPlatform()));
    }
    public WebDriver getDesktopDriver(String browser) {
        if(browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--remote-allow-origins=*");
           // chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--user-data-dir=/tmp/chrome-profile");
            driver = new ChromeDriver(chromeOptions);
            logger.info("************** Launching Chrome browser ****************");
        } else if(browser.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriver();
        } else if(browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if(browser.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
            driver.manage().window().maximize();
        } else if(browser.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        }
        return driver;
    }

    public AppiumDriver getAppiumDriver(String platform) throws IOException, InterruptedException {
        logger.info("Launching Appium Server");
        Runtime.getRuntime().exec("appium --address 127.0.0.1 --port 4723");
        Thread.sleep(10000);
        if(appiumDriver==null) {
            switch(platform) {
                case "iOS":
                    capabilities.setCapability("deviceName",propertyReader.getMobileProperty("APPLE_RD_DEVICENAME"));
                    capabilities.setCapability("platformName",propertyReader.getMobileProperty("APPLE_PLATFORMNAME"));
                    capabilities.setCapability("platformVersion",propertyReader.getMobileProperty("APPLE_RD_PLATFORMVERSION"));
                    capabilities.setCapability("app",propertyReader.getMobileProperty("APPLE_SETTINGS"));
                    capabilities.setCapability("udid",propertyReader.getMobileProperty("APPLE_RD_UDID"));
                    capabilities.setCapability("noReset",true);
                    capabilities.setCapability("automationName",propertyReader.getMobileProperty("APPLE_AUTOMATIONNAME"));
                    try {
                        appiumDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "iOSSimulator":
                    capabilities.setCapability("deviceName",propertyReader.getMobileProperty("APPLE_SD_DEVICENAME"));
                    capabilities.setCapability("platformName",propertyReader.getMobileProperty("APPLE_PLATFORMNAME"));
                    capabilities.setCapability("platformVersion",propertyReader.getMobileProperty("APPLE_SD_PLATFORMVERSION"));
                    capabilities.setCapability("app",propertyReader.getMobileProperty("APPLE_SETTINGS"));
                    capabilities.setCapability("noReset",true);
                    capabilities.setCapability("automationName",propertyReader.getMobileProperty("APPLE_AUTOMATIONNAME"));
                    try {
                        appiumDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Android":
                    String appLocation = System.getProperty("user.dir") + "/apkFiles/CottonTraders.apk";
                    capabilities.setCapability("deviceName","ANDROID_DEVICE_NAME");
                    capabilities.setCapability("platformName",propertyReader.getMobileProperty("ANDROID_PLATFORM_NAME"));
                    capabilities.setCapability("platformVersion",propertyReader.getMobileProperty("ANDROID_VERSION"));
                    capabilities.setCapability("app",appLocation);
                    capabilities.setCapability("appPackage", propertyReader.getMobileProperty("ANDROID_APP_PACKAGE"));
                    capabilities.setCapability("noReset",false);
                    capabilities.setCapability("automationName",propertyReader.getMobileProperty("ANDROID_TEST_NAME"));
                    try {
                        appiumDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    break;

            }
        } else {
            logger.error("Failed to initialize Appium Driver");
        }
        return appiumDriver;
    }
    public void cleanUpCurrentDriver() {
        currentDriver.remove();
    }
}
