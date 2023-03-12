package org.redwind.beluga.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.redwind.testAuto.beluga.utils.Reporter;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

@CucumberOptions (features = "src/test/resources/features",
        glue = {"org/redwind/testAuto/beluga/steps"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:reports/testResult.html"},
        tags = "@WebServicesExample"
        )
public class FeatureRunner extends AbstractTestNGCucumberTests
{
        @BeforeMethod
        public void setup(ITestContext test) {
                Reporter.startReport(test);
        }
}
