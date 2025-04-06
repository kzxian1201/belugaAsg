package org.redwind.beluga.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (features = "src/test/resources/features",
        glue = {"org.redwind.autotest.beluga.steps", "org.redwind.autotest.beluga.utils"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:reports/index.html"},
        tags = "@WebServiceExample"
        )
public class FeatureRunner extends AbstractTestNGCucumberTests {

}
