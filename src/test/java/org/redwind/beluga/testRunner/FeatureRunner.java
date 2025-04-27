package org.redwind.beluga.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (features = "src/test/resources/features/mobileApp/SampleAndriodTest.features",
        glue = {"org.redwind.autotest.beluga.steps", "org.redwind.autotest.beluga.utils"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:reports/testResult.html"},
        tags = "@WebServiceExample"
        )
public class FeatureRunner extends AbstractTestNGCucumberTests {

}
