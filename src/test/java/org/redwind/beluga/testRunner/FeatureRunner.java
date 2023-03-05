package org.redwind.beluga.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions (features = "src/test/resources/features",
        glue = {"org/redwind/testAuto/beluga/steps"},
        tags = "@WebServicesExample"
        )
public class FeatureRunner extends AbstractTestNGCucumberTests
{

}
