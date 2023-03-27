package org.redwind.beluga.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (features = "src/test/resources/features",
        glue = {"org.redwind.testAuto.beluga.steps", "org.redwind.testAuto.beluga.utils"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:reports/testResult.html"},
        tags = "@WebServiceExample"
        )
public class FeatureRunner extends AbstractTestNGCucumberTests {

}
