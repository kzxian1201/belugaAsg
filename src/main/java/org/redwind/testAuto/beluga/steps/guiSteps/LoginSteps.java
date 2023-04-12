package org.redwind.testAuto.beluga.steps.guiSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;
import org.redwind.testAuto.beluga.pages.webApp.LoginPage;
import org.redwind.testAuto.beluga.pages.webApp.MainLandingPage;
import org.redwind.testAuto.beluga.utils.Encryption;
import org.redwind.testAuto.beluga.utils.PropertyReader;
import org.redwind.testAuto.beluga.utils.WrapperMethods;

import java.io.IOException;
import java.util.List;

public class LoginSteps extends WrapperMethods {

    private PropertyReader propertyReader = new PropertyReader();
    @Given("Open the application in the browser")
    public void openTheApplication() throws IOException {
        openApplication(propertyReader.getGuiProperty("appURL"));
    }
    @Then("User logs with {string}")
    public void loginToApplication(String username) throws IOException{
        String name = username.replace(" ","_").toLowerCase();
        logger.info(name);
        enterText(LoginPage.USERNAME,propertyReader.getGuiProperty(name));
        enterText(LoginPage.PASSWORD,new Encryption().decrypt(propertyReader.getGuiProperty("password")));
        clickOnElement(LoginPage.LOGIN_BUTTON);
        String mainHeading = getTextFromElement(MainLandingPage.MAIN_HEADING);
        logger.info("Text %s", mainHeading);
        List<WebElement> options = getAllDropdownValues(MainLandingPage.FILTER_VALUE);
        for(int i=0;i<options.size();i++) {
            logger.info("Values are %s", options.get(i).getText());
        }
        openNewWindow();
    }

}
