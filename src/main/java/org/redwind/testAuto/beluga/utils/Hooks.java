package org.redwind.testAuto.beluga.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.redwind.testAuto.beluga.configuration.DriverFactory;

import java.io.IOException;

public class Hooks {

    private Scenario scenario;
    private Logger logger = LogManager.getFormatterLogger();
    DriverFactory driverFactory = new DriverFactory();
    private PropertyReader propertyReader = new PropertyReader();

    Environment environment;

    {
        try {
            environment = propertyReader.getEnvironment();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Before
    public void scenarioStartUp(Scenario scenario) throws IOException {
        startLogger(scenario);
        if(environment.getPlatform()!="Restful") {
            driverFactory.initializeBrowser();
        }
    }

    private void startLogger(Scenario scenario) {
        logger.info("**************************************************************************");
        logger.info("$$$$$$$$$$$$$$$$$$$$$ Starting Scenario ----- %s $$$$$$$$$$$$$$$",scenario.getName());
        logger.info("**************************************************************************");
    }

    private void endLogger(Scenario scenario) {
        logger.info("**************************************************************************");
        logger.info("$$$$$$$$$$$$$$$$$$$$$ Ending Scenario ----- %s $$$$$$$$$$$$$$$",scenario.getName());
        logger.info("**************************************************************************");
    }

    @After
    public void scenarioTailEnd(Scenario scenario) throws IOException {
        endLogger(scenario);
        if(environment.getPlatform()!="Restful") {
            driverFactory.getCurrentDriver().quit();
        }
    }



}
